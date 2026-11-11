# Atlas Bank

API REST de un banco de ejemplo, desarrollada con Spring Boot para practicar patrones de diseño, Domain-Driven Design y arquitectura hexagonal. Gestiona cuentas y transferencias con comisiones, verificación de fraude y autenticación con Keycloak.

El proyecto se migró de una **arquitectura en capas** a una **arquitectura hexagonal** (puertos y adaptadores), de modo que el dominio no depende de Spring, JPA ni de ningún servicio externo.

> **Estado:** proyecto de aprendizaje en desarrollo. No está pensado para usarse en producción.

## Características

- Migración de arquitectura en capas a arquitectura hexagonal.
- Dominio independiente del framework: los objetos del dominio se registran como beans desde `DomainBeanConfig`, sin anotaciones de Spring.
- Value Objects (`Money`, `Email`) con validación e inmutabilidad.
- Eventos de dominio (`TransactionExecutedEvent`) registrados por el agregado y publicados por el adaptador de persistencia.
- Transferencias con comisiones según el tipo de cuenta y validaciones encadenadas.
- Verificación de fraude mediante un servicio externo simulado.
- Autenticación y autorización con JWT de Keycloak.

## Tecnologías

- Java 25
- Spring Boot 4.1 (Web MVC, Data JPA, Validation, OAuth2 Resource Server)
- H2 como base de datos en archivo
- Keycloak 26 para autenticación (JWT)
- Lombok y MapStruct
- Docker Compose y Maven

## Requisitos

- JDK 25
- Docker con Docker Compose

Maven no es obligatorio, porque el proyecto incluye Maven Wrapper (`mvnw`).

## Puesta en marcha

### 1. Configurar las contraseñas de Docker

Copia el archivo de ejemplo y define tus contraseñas en `docker/.env`:

```bash
cd docker
cp .env.example .env
```

En PowerShell también funciona `cp`; en CMD usa `copy .env.example .env`.

### 2. Levantar Keycloak

Desde la carpeta `docker`:

```bash
docker compose up -d
```

La consola de Keycloak queda en <http://localhost:8181>. El usuario es `admin` y la contraseña es la que definiste en `KC_ADMIN_PASSWORD`.

### 3. Configurar Keycloak

El realm todavía no se incluye en el repositorio, así que hay que crearlo a mano:

1. Crea un realm llamado `atlas-bank`.
2. Crea los roles de realm `ROLE_ADMIN` y `ROLE_USER`, con el prefijo incluido.
3. Crea un cliente para obtener tokens.
4. Crea los usuarios y asígnales un rol.

### 4. Ejecutar la aplicación

Desde la raíz del proyecto:

```bash
./mvnw spring-boot:run
```

La API queda disponible en <http://localhost:8080>. La base de datos se crea automáticamente en `data/`.

## Endpoints

Todas las peticiones requieren la cabecera `Authorization: Bearer <token>`.

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/v1/accounts` | ADMIN | Crear una cuenta |
| `GET` | `/api/v1/accounts` | ADMIN | Listar las cuentas |
| `GET` | `/api/v1/accounts/{id}` | USER, ADMIN | Consultar una cuenta |
| `GET` | `/api/v1/accounts/{id}/dashboard` | Cualquier usuario autenticado | Resumen de la cuenta, movimientos y fraude |
| `POST` | `/api/v1/transactions/transfer` | USER, ADMIN | Realizar una transferencia |
| `GET` | `/api/v1/transactions/{id}/transactions` | USER, ADMIN | Consultar los movimientos de una cuenta |

La consola de H2 está en <http://localhost:8080/h2-console> (JDBC URL `jdbc:h2:file:./data/atlasbank`, usuario `sa`, sin contraseña).

## Arquitectura

### De capas a hexagonal

Al principio, el proyecto estaba organizado por funcionalidad (`account`, `transaction`, `shared`), con la arquitectura en capas clásica:

```text
Controller → Service → Repository (Spring Data) → Base de datos
```

En ese modelo, las entidades JPA eran también el modelo de negocio, y los servicios dependían directamente de Spring Data.

Con la migración a arquitectura hexagonal, el dominio queda en el centro y todo lo técnico se conecta mediante puertos y adaptadores:

```text
Adaptadores de entrada (REST)
        │ usan
        ▼
Puertos de entrada (casos de uso)
        │ los implementa
        ▼
Aplicación ──► Dominio
        │ usa
        ▼
Puertos de salida (interfaces)
        ▲ los implementan
        │
Adaptadores de salida (JPA, servicio de fraude)
```

Las dependencias siempre apuntan hacia dentro: el dominio no importa nada de `application` ni de `infrastructure`.

### Conceptos aplicados

| Concepto | En el proyecto |
|---|---|
| Puerto de entrada | `TransferMoneyUseCase`, `CreateAccountUseCase` |
| Puerto de salida | `AccountRepositoryPort`, `TransactionRepositoryPort`, `CustomerRepositoryPort`, `FraudCheckPort` |
| Adaptador de entrada | `AccountController`, `TransactionController` |
| Adaptador de salida | `JpaAccountRepositoryAdapter`, `JpaTransactionRepositoryAdapter`, `JpaCustomerRepositoryAdapter`, `ExternalFraudCheckAdapter` |
| Agregado | `Account`, `Transaction`, `Customer` |
| Value Object | `Money`, `Email` y los `enum` (`Currency`, `AccountStatus`, `AccountType`…) |
| Servicio de dominio | `TransferDomainService` |
| Evento de dominio | `TransactionExecutedEvent`, escuchado por `AuditListener` y `NotificationListener` |

### Modelo de dominio y modelo de persistencia

Cada agregado tiene una versión de dominio y una entidad JPA, conectadas por un mapper manual:

```text
Account (dominio)  ⇄  AccountPersistenceMapper  ⇄  AccountJpaEntity (@Entity)
```

`Money` y `Email` existen en dos versiones: la del dominio (`domain.model.shared`) y una `@Embeddable` para JPA (`infrastructure.adapter.out.persistence`).

## Patrones de diseño

| Patrón | Dónde se aplica |
|---|---|
| Strategy | `FeeCalculator`: comisión según el tipo de cuenta |
| Template Method | `TransactionProcessor`: flujo de una transacción |
| Factory Method | `TransactionFactory`: construcción de transacciones |
| Observer | `TransactionExecutedEvent` y sus listeners |
| Decorator | `CachedAccountService` y `AuditableAccountService` |
| Adapter | Todos los adaptadores; por ejemplo, `ExternalFraudCheckAdapter` para el servicio de fraude externo (simulado) |
| Facade | `AccountDashboardFacade`: datos del dashboard |
| Builder | `DashboardResponse` |
| Chain of Responsibility | `TransferValidator`: validaciones de una transferencia |
| State | `TransactionState`: ciclo de vida de una transacción |

## Estructura

```text
src/main/java/com/atlas/bank
├── domain              # Núcleo del negocio, sin dependencias de Spring
│   ├── model           # Agregados y Value Objects: Account, Transaction, Customer, Money, Email
│   ├── service         # Servicios de dominio: TransferDomainService
│   ├── event           # Eventos de dominio: TransactionExecutedEvent
│   ├── exception       # Excepciones del negocio
│   ├── strategy        # Cálculo de comisiones
│   └── validation      # Validaciones de transferencias
├── application         # Casos de uso
│   ├── port/in         # Puertos de entrada: lo que ofrece la aplicación
│   ├── port/out        # Puertos de salida: lo que necesita del exterior
│   └── service         # Implementación de los casos de uso
└── infrastructure      # Detalles técnicos
    ├── adapter/in      # Controllers REST, DTOs, caché y fachada del dashboard
    ├── adapter/out     # Persistencia con JPA y servicio de fraude externo
    ├── adapter/listener # Listeners de los eventos de dominio
    └── config          # Seguridad, manejo de errores y registro de beans del dominio
```

## Pendientes

- Quitar las anotaciones de JPA que aún quedan en `domain/model/account/Account.java`.
- Usar `@TransactionalEventListener(phase = AFTER_COMMIT)` en los listeners, para notificar solo transferencias confirmadas.
- Comprobar que un USER solo pueda acceder a sus propias cuentas.
- Invalidar la caché de cuentas después de una transferencia.
- Incluir la configuración del realm de Keycloak en el repositorio.
- Conectar Keycloak a la base de datos Postgres de Docker Compose.
- Añadir tests.

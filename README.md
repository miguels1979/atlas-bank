# Atlas Bank

API REST de un banco de ejemplo, desarrollada con Spring Boot para practicar patrones de diseño. Gestiona cuentas y transferencias con comisiones, verificación de fraude y autenticación con Keycloak.

> **Estado:** proyecto de aprendizaje en desarrollo. No está pensado para usarse en producción.

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

## Patrones de diseño

| Patrón | Dónde se aplica |
|---|---|
| Strategy | `FeeCalculator`: comisión según el tipo de cuenta |
| Template Method | `TransactionProcessor`: flujo de una transacción |
| Factory Method | `TransactionFactory`: construcción de transacciones |
| Observer | `TransactionExecutedEvent` y sus listeners |
| Decorator | `CachedAccountService` y `AuditableAccountService` |
| Adapter | `ExternalFraudCheckAdapter`: servicio de fraude externo (simulado) |
| Facade | `AccountDashboardFacade`: datos del dashboard |
| Builder | `DashboardResponse` |
| Chain of Responsibility | `TransferValidator`: validaciones de una transferencia |
| State | `TransactionState`: ciclo de vida de una transacción |

## Estructura

```text
src/main/java/com/atlas/bank
├── account      # Cuentas: controller, servicios, modelo y DTOs
├── transaction  # Transferencias: comisiones, fraude, eventos y validaciones
└── shared       # Configuración de seguridad y manejo de errores
```

## Pendientes

- Comprobar que un USER solo pueda acceder a sus propias cuentas.
- Invalidar la caché de cuentas después de una transferencia.
- Incluir la configuración del realm de Keycloak en el repositorio.
- Conectar Keycloak a la base de datos Postgres de Docker Compose.
- Añadir tests.

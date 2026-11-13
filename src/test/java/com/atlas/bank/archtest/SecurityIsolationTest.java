package com.atlas.bank.archtest;


import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.atlas.bank", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecurityIsolationTest {

    // ── Regla 1: El dominio no conoce Spring Security ──

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring_security =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("org.springframework.security..")
                    .because("La seguridad es un adapter de infraestructura - el dominio no sabe que Keycloak existe");


    // ── Regla 2: La aplicación no conoce Spring Security ──
    @ArchTest
    static final ArchRule domain_should_not_depend_on_application =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("org.springframework.security..")
                    .because("La autorización se resuelve en el adapter antes de invocar el use case");



}

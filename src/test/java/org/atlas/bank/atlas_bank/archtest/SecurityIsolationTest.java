package org.atlas.bank.atlas_bank.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "org.atlas.bank.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class SecurityIsolationTest {
    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring_security = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("org.springframework.security..")
            .because("Domain layer should not depend on Spring Security");

    @ArchTest
    static final ArchRule application_should_not_depend_on_spring_security = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAnyPackage("org.springframework.security..")
            .because("Application layer should not depend on Spring Security");
}

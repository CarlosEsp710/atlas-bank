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
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domain_layer_should_not_depend_on_infrastructure_layer = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
            .because("Domain layer should not depend on Infrastructure layer");

    @ArchTest
    static final ArchRule domain_layer_should_not_depend_on_application_layer = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..application..")
            .because("Domain layer should not depend on Application layer");

    @ArchTest
    static final ArchRule application_layer_should_not_depend_on_infrastructure_layer = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
            .because("Application layer should not depend on Infrastructure layer");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring_framework = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("org.springframework..")
            .because("Domain layer should not depend on Spring Framework");
}

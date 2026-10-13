package org.atlas.bank.atlas_bank.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "org.atlas.bank.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NamingConventionTest {

    @ArchTest
    static final ArchRule controllers_should_reside_in_rest_adapter_package = classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..infrastructure.adapter.in.rest")
            .because("Controllers should reside in the infrastructure.adapter.in.rest package");

    @ArchTest
    static final ArchRule use_cases_should_reside_in_port_in_package = classes()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().resideInAPackage("..application.port.in")
            .because("Use cases should reside in the application.port.in package");

    @ArchTest
    static final ArchRule port_in_should_be_interfaces = classes()
            .that().resideInAPackage("..application.port.in")
            .should().beInterfaces()
            .because("Ports in should be interfaces");

    @ArchTest
    static final ArchRule port_out_should_be_interfaces = classes()
            .that().resideInAPackage("..application.port.out")
            .should().beInterfaces()
            .because("Ports out should be interfaces");
}

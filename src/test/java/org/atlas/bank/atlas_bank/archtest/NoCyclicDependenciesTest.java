package org.atlas.bank.atlas_bank.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(
        packages = "org.atlas.bank.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NoCyclicDependenciesTest {
    @ArchTest
    static final ArchRule domain_model_should_not_have_cyclic_dependencies = slices()
            .matching("org.atlas.bank.atlas_bank.domain.model.(*)..")
            .should().beFreeOfCycles()
            .because("Domain model should not have cyclic dependencies");

    @ArchTest
    static final ArchRule layers_should_not_have_cyclic_dependencies = slices()
            .matching("org.atlas.bank.atlas_bank.(*)..")
            .should().beFreeOfCycles()
            .because("Layers should not have cyclic dependencies");
}

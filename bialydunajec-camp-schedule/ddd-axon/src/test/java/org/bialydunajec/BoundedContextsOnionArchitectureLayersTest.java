package org.bialydunajec;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.bialydunajec", importOptions = {ImportOption.DontIncludeTests.class, ImportOption.DontIncludeJars.class})
public class BoundedContextsOnionArchitectureLayersTest {

    private static final String DOMAIN_LAYER_NAME = "Domain";
    private static final String APPLICATION_LAYER_NAME = "Application";
    private static final String PRESENTATION_LAYER_NAME = "Presentation";
    private static final String INFRASTRUCTURE_LAYER_NAME = "Infrastructure";

    @ArchTest
    public static final ArchRule dddLayeredArchitecture =
            layeredArchitecture()
                    .layer(DOMAIN_LAYER_NAME).definedBy("..domain..")
                    .layer(APPLICATION_LAYER_NAME).definedBy("..application..")
                    .layer(PRESENTATION_LAYER_NAME).definedBy("..presentation..")
                    .layer(INFRASTRUCTURE_LAYER_NAME).definedBy("..infrastructure..")
                    .whereLayer(DOMAIN_LAYER_NAME).mayOnlyBeAccessedByLayers(PRESENTATION_LAYER_NAME, APPLICATION_LAYER_NAME, INFRASTRUCTURE_LAYER_NAME)
                    .whereLayer(APPLICATION_LAYER_NAME).mayOnlyBeAccessedByLayers(PRESENTATION_LAYER_NAME, INFRASTRUCTURE_LAYER_NAME)
                    .whereLayer(INFRASTRUCTURE_LAYER_NAME).mayNotBeAccessedByAnyLayer();

    /*TODO: After domain distillation
    @ArchTest
    public static final ArchRule domainLayerShouldBeSpringFrameworkAgnostic =
            noClasses()
                    .that()
                    .resideInAPackage("..domain..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..springframework..");*/

}

package org.bialydunajec.configuration.swagger

import com.google.common.collect.Lists
import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.SecurityConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
internal class SwaggerConfiguration {

    /*private val globalOperationHeaders: List<Parameter>
        get() = listOf(authorizationHeader)

    private val authorizationHeader: Parameter
        get() = ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Header for authentication preceded by 'bearer' keyword.")
                .modelRef(ModelRef("string"))
                .parameterType(HEADER_PARAMETER_TYPE)
                .required(false)
                .build()
                */


    @Bean
    internal fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.globalOperationParameters(globalOperationHeaders)
                .ignoredParameterTypes(UserDetailsDto::class.java)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
    }

    //TODO: Add secuirty with username and password instead of header!
    @Bean
    fun security(): SecurityConfiguration {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("apiKey", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope(
                "global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Arrays.asList(SecurityReference("apiKey", authorizationScopes))
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Biały Dunajec Aplikacja Webowa - REST Api")
                .version("0.0.2")
                .contact(Contact("Administrator Systemu Zapisów", "bialydunajec.org", "zapisy@bialydunajec.org"))
                .build();
    }


    companion object {

        private val HEADER_PARAMETER_TYPE = "header"
    }
}
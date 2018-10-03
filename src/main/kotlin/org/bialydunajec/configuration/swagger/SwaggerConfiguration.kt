package org.bialydunajec.configuration.swagger

import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
internal class SwaggerConfiguration {

    private val globalOperationHeaders: List<Parameter>
        get() = listOf(authorizationHeader)

    private val authorizationHeader: Parameter
        get() = ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Header for authentication preceded by 'bearer' keyword.")
                .modelRef(ModelRef("string"))
                .parameterType(HEADER_PARAMETER_TYPE)
                .required(false)
                .build()


    @Bean
    internal fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(globalOperationHeaders)
                .ignoredParameterTypes(UserDetailsDto::class.java)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Biały Dunajec Aplikacja Webowa - REST Api!")
                .version("0.0.1")
                .contact("zapisy@bialydunajec.org")
                .build();
    }


    companion object {

        private val HEADER_PARAMETER_TYPE = "header"
    }
}
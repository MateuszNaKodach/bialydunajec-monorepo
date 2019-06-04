package org.bialydunajec.news.infrastructure.facebook

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Validated
@Configuration
@ConfigurationProperties(prefix = "bialydunajec.facebook")
open class BialyDunajecFacebookProperties {

    lateinit var accessToken: String

}
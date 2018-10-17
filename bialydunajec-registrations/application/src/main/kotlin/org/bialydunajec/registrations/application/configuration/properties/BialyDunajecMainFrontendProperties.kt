package org.bialydunajec.registrations.application.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Configuration
@ConfigurationProperties(prefix = "bialydunajec.frontend.main")
class BialyDunajecMainFrontendProperties {

    @NotBlank
    lateinit var baseUrl: String

    @NotBlank
    lateinit var registrationVerificationUrl: String
}
package org.bialydunajec.gallery.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Validated
@Configuration
@ConfigurationProperties(prefix = "bialydunajec.google-photos-library-api")
class BialyDunajecGoogleProperties {

    lateinit var refreshToken: String
    lateinit var credentialsJsonPath: String

}

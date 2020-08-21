package org.bialydunajec.gallery.infrastructure

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@ConditionalOnProperty(name = ["bialydunajec.camp-gallery-provider"], havingValue = "google-photos")
@Validated
@Configuration
@ConfigurationProperties(prefix = "bialydunajec.google-photos-library-api")
class BialyDunajecGoogleProperties {

    lateinit var refreshToken: String
    lateinit var credentialsJsonPath: String

}

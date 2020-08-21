package org.bialydunajec.authorization.server.security.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Validated
@Configuration
@ConfigurationProperties(prefix = "bialydunajec.oauth2")
class BialyDunajecOAuth2Properties {

    var clients: Array<OAuth2Client> = emptyArray()

    class OAuth2Client {

        @NotBlank
        lateinit var clientId: String

        @NotBlank
        lateinit var clientSecret: String

        @NotEmpty
        lateinit var authorizedGrantTypes: Array<String>

        @NotNull
        var accessTokenValiditySeconds: Int = 0

        @NotNull
        var refreshTokenValiditySeconds: Int = 0

        @NotEmpty
        lateinit var scopes: Array<String>
    }


}
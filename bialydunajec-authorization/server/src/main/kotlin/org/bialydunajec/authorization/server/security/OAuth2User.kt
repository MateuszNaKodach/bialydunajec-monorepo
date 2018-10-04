package org.bialydunajec.authorization.server.security


import org.bialydunajec.authorization.server.api.dto.exception.AuthorizationErrorCode
import org.bialydunajec.authorization.server.api.dto.exception.AuthorizationServerException
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
internal data class OAuth2User(
        @EmbeddedId
        private val oAuth2UserId: OAuth2UserId = OAuth2UserId(),

        @NotBlank
        @Column(unique = true, updatable = true)
        private var emailAddress: String,

        @NotBlank
        @Column(unique = true, updatable = true)
        private var username: String,

        @NotBlank
        private var password: String,

        @NotNull
        private var enabled: Boolean = true
) {

    fun updateEmailAddress(emailAddress: String) {
        this.emailAddress = emailAddress
    }

    fun updateUsername(username: String) {
        this.username = username
    }

    fun updatePassword(oldPlainPassword: String, newPlainPassword: String, passwordEncoder: PasswordEncoder) {
        if (!passwordEncoder.matches(oldPlainPassword, password)) {
            throw AuthorizationServerException.of(AuthorizationErrorCode.INVALID_USER_PASSWORD)
        }
        this.password = passwordEncoder.encode(newPlainPassword)
    }

    fun disable() {
        enabled = false;
    }

    fun enable() {
        enabled = true;
    }

    fun getOAuth2UserId() = oAuth2UserId
    fun getEmailAddress() = emailAddress
    fun getUsername() = username
    fun getPassword() = password
    fun isEnabled() = enabled
    fun getSnapshot() = OAuth2UserSnapshot(oAuth2UserId.toString(), emailAddress, username, password, enabled)

}
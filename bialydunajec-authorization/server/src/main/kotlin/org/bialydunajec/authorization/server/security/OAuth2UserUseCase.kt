package org.bialydunajec.authorization.server.security

import org.bialydunajec.authorization.server.api.dto.exception.AuthorizationErrorCode
import org.bialydunajec.authorization.server.api.dto.exception.AuthorizationServerException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

internal class OAuth2UserCreator(
        private val oAuth2UserRepository: OAuth2UserRepository,
        private val passwordEncoder: PasswordEncoder) {

    fun createOAuth2UserClaims(emailAddress: String, username: String?, plainPassword: String) =
            oAuth2UserRepository.save(
                    OAuth2User(
                            emailAddress = emailAddress,
                            username = username,
                            password = passwordEncoder.encode(plainPassword)
                    )
            ).getOAuth2UserId()

}

@Service
internal class OAuth2UserFinder(private val oAuth2UserUserRepository: OAuth2UserRepository)  {

    fun findByUsernameOrEmailAddress(usernameOrEmailAddress: String): OAuth2User =
            oAuth2UserUserRepository.findByUsernameOrEmailAddress(usernameOrEmailAddress, usernameOrEmailAddress)
                    ?: throw AuthorizationServerException.of(AuthorizationErrorCode.USER_CREDENTIALS_NOT_FOUND, UsernameNotFoundException(usernameOrEmailAddress))

    fun findByUserId(userId: OAuth2UserId): OAuth2User =
            oAuth2UserUserRepository.findById(userId).orElseThrow { AuthorizationServerException.of(AuthorizationErrorCode.USER_CREDENTIALS_NOT_FOUND) }

}

@Service
internal class OAuth2CurrentUserGetter(private val oAuth2UserUserRepository: OAuth2UserRepository) {

    fun getCurrentUser(): OAuth2User {
        var loggedUserUsername: String? = null
        val context = SecurityContextHolder.getContext()
        if (context != null) {
            val authentication = context.authentication
            if (authentication != null) {
                loggedUserUsername = authentication.name
            }
        }
        return if (loggedUserUsername != null) oAuth2UserUserRepository.findByUsername(loggedUserUsername)
                ?: throw AuthorizationServerException.of(AuthorizationErrorCode.NO_LOGGED_USER)
        else
            throw AuthorizationServerException.of(AuthorizationErrorCode.NO_LOGGED_USER)
    }
}


internal class OAuth2UserUpdater(
        private val oAuth2UserRepository: OAuth2UserRepository,
        private val passwordEncoder: PasswordEncoder) {

    fun updateOAuth2UserPassword(userId: OAuth2UserId, oldPlainPassword: String, newPlainPassword: String) {
        val user = tryToFindOAuth2UserById(userId)
        user.updatePassword(oldPlainPassword, newPlainPassword, passwordEncoder)
        oAuth2UserRepository.save(user)
    }

    fun updateOAuth2UserEmailAddress(userId: OAuth2UserId, newEmailAddress: String) {
        val user = tryToFindOAuth2UserById(userId)
        user.updateEmailAddress(newEmailAddress)
        oAuth2UserRepository.save(user)
    }

    fun updateOAuth2UserUsername(userId: OAuth2UserId, newUsername: String) {
        val user = tryToFindOAuth2UserById(userId)
        user.updateUsername(newUsername)
        oAuth2UserRepository.save(user)
    }

    private fun tryToFindOAuth2UserById(userId: OAuth2UserId) = oAuth2UserRepository.findById(userId)
            .orElseThrow { AuthorizationServerException.of(AuthorizationErrorCode.USER_CREDENTIALS_NOT_FOUND) }

}
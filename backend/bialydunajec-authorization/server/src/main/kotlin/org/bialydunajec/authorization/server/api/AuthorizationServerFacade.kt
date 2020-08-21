package org.bialydunajec.authorization.server.api

import org.bialydunajec.authorization.server.api.command.*
import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.bialydunajec.authorization.server.api.query.UserByIdQuery
import org.bialydunajec.authorization.server.api.query.UserByUsernameOrEmailAddressQuery
import org.bialydunajec.authorization.server.security.*

class AuthorizationServerFacade internal constructor(
        private val oAuth2UserCreator: OAuth2UserCreator,
        private val oAuth2UserFinder: OAuth2UserFinder,
        private val oAuth2UserUpdater: OAuth2UserUpdater,
        private val currentUserGetter: OAuth2CurrentUserGetter
        // private val resetOAuth2UserPasswordEmailSender: ResetOAuth2UserPasswordEmailSender
) {

    fun createUserCredentials(command: CreateUserCredentialsCommand) =
            oAuth2UserCreator.createOAuth2UserClaims(
                    emailAddress = command.emailAddress,
                    username = command.username,
                    plainPassword = command.password
            )

    fun updateUserEmailAddress(command: UpdateUserEmailAddressCommand) =
            oAuth2UserUpdater.updateOAuth2UserEmailAddress(
                    userId = OAuth2UserId(command.userId),
                    newEmailAddress = command.emailAddress
            )

    fun updateUserPassword(command: UpdateUserPasswordCommand) {
        oAuth2UserUpdater.updateOAuth2UserPassword(
                userId = OAuth2UserId(command.userId),
                oldPlainPassword = command.oldPassword,
                newPlainPassword = command.newPassword
        )
    }

    fun updateUserUsername(command: UpdateUserUsernameCommand) {
        oAuth2UserUpdater.updateOAuth2UserUsername(
                userId = OAuth2UserId(command.userId),
                newUsername = command.username
        )
    }

    // fun sendResetUserPasswordEmailMessage(command: SendResetUserPasswordEmailMessageCommand) {
    //     resetOAuth2UserPasswordEmailSender.sendResetUserPasswordEmailMessage(command.userId)
    // }

    fun findUserById(query: UserByIdQuery) =
            UserDetailsDto.createFrom(oAuth2UserFinder.findByUserId(OAuth2UserId(query.userId)).getSnapshot())

    fun findUserByUsernameOrEmailAddress(query: UserByUsernameOrEmailAddressQuery) =
            UserDetailsDto.createFrom(oAuth2UserFinder.findByUsernameOrEmailAddress(query.usernameOrEmailAddress).getSnapshot())

    fun tryGetCurrentUser() = UserDetailsDto.createFrom(currentUserGetter.tryGetCurrentUser().getSnapshot())

    fun getCurrentUser() = currentUserGetter.getCurrentUser()

}
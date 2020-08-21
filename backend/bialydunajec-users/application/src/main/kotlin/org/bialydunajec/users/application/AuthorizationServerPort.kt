package org.bialydunajec.users.application

import org.bialydunajec.users.domain.UserCredentialsId

interface AuthorizationServerPort {

    fun createUserCredentials(username: String, plainPassword: String): UserCredentialsId

}
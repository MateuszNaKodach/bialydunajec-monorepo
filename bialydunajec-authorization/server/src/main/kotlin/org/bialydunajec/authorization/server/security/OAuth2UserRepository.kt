package org.bialydunajec.authorization.server.security

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface OAuth2UserRepository : JpaRepository<OAuth2User, OAuth2UserId> {
    fun findByUsernameOrEmailAddress(username: String, emailAddress: String): OAuth2User?
    fun findByUsername(username: String): OAuth2User?
}
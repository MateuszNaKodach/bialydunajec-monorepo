package org.bialydunajec.authorization.server.api.dto

import org.bialydunajec.authorization.server.security.OAuth2UserSnapshot
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsDto(
        private val userId: String,
        private val emailAddress: String,
        private val username: String,
        private val password: String,
        private val enabled: Boolean
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf<GrantedAuthority>(SimpleGrantedAuthority("USER"))

    fun getUserId() = userId

    fun getEmailAddress(): String = emailAddress

    override fun getUsername(): String = username

    override fun getPassword(): String = password

    override fun isEnabled(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean = enabled

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    internal companion object {
        fun createFrom(oAuth2UserSnapshot: OAuth2UserSnapshot) = UserDetailsDto(oAuth2UserSnapshot.userId, oAuth2UserSnapshot.emailAddress, oAuth2UserSnapshot.username, oAuth2UserSnapshot.password, oAuth2UserSnapshot.enabled)
    }
}
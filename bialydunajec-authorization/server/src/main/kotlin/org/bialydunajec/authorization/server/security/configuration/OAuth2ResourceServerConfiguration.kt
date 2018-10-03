package org.bialydunajec.authorization.server.security.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

@EnableResourceServer
@Configuration
internal class OAuth2ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/h2-console/**", "/swagger-ui.html", "/oauth/token", "api/v1/user-account")
                .permitAll()
                .antMatchers("/api/v1/admin/**")
                .authenticated()
                .antMatchers("/api/v1/campers-register/in-progress/**").permitAll()
        //.access("#oauth2.isClient()");

        http.csrf().disable()
        http.headers().frameOptions().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        super.configure(resources)
    }
}
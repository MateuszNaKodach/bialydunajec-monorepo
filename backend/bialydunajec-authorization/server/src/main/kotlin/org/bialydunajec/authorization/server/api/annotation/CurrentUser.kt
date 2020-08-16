package org.bialydunajec.authorization.server.api.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AuthenticationPrincipal
annotation class CurrentUser
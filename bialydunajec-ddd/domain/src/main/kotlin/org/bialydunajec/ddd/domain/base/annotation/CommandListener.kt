package org.bialydunajec.ddd.domain.base.annotation

import org.springframework.context.event.EventListener

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@EventListener
annotation class CommandListener
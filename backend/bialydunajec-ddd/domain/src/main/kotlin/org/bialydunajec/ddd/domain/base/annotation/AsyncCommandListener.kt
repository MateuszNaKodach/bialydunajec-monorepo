package org.bialydunajec.ddd.domain.base.annotation

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@EventListener
@Async
annotation class AsyncCommandListener
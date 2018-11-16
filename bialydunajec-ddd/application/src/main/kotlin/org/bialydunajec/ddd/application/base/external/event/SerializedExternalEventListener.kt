package org.bialydunajec.ddd.application.base.external.event

import org.bialydunajec.ddd.application.base.concurrency.ProcessingSerializedQueue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

//TODO: Add logic for recording event to internal storage
//TODO: Consider solution - one thread for one aggregate. Also one method for one aggregate type in listeners
//TODO: Zrobic jakos warstwe posrednia, subskrypcja do eventbusa, rxJava!!! Potem np. podmiana implementacji - RabbitMQ to wrzuca do subjecta!
abstract class SerializedExternalEventListener : ExternalEventListener {

    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private val processingQueue = ProcessingSerializedQueue<ExternalEvent<*>> { processExternalEvent(it) }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    override fun handleExternalEvent(externalEvent: ExternalEvent<*>) {
        processingQueue.process(externalEvent)
    }

    abstract fun processExternalEvent(externalEvent: ExternalEvent<*>)
}
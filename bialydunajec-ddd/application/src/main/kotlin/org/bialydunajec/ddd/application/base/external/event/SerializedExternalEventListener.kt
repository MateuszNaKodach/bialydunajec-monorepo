package org.bialydunajec.ddd.application.base.external.event

import org.bialydunajec.ddd.application.base.concurrency.ProcessingSerializedQueue

//TODO: Add logic for recording event to internal storage
//TODO: Consider solution - one thread for one aggregate. Also one method for one aggregate type in listeners
//TODO: Zrobic jakos warstwe posrednia, subskrypcja do eventbusa, rxJava!!! Potem np. podmiana implementacji - RabbitMQ to wrzuca do subjecta!
abstract class SerializedExternalEventListener : ExternalEventListener {

    protected val processingQueue = ProcessingSerializedQueue<ExternalEvent<*>> { processExternalEvent(it) }

    abstract fun processExternalEvent(externalEvent: ExternalEvent<*>)
}

package org.bialydunajec.ddd.application.base.external.event

import io.reactivex.Observable

interface ExternalEventObserver {

    fun <T> observeForEvent(eventClass: Class<T>): Observable<T>
}

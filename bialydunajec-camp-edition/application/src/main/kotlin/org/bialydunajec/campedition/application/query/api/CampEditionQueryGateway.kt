package org.bialydunajec.campedition.application.query.api

import org.bialydunajec.campedition.application.query.readmodel.DomainModelReader
import org.springframework.stereotype.Component

@Component
class CampEditionQueryGateway internal constructor(private val domainModelReader: DomainModelReader) {

    fun process(query: CampEditionQuery.All) = domainModelReader.readFor(query)
    fun process(query: CampEditionQuery.ById) = domainModelReader.readFor(query)
}
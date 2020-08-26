package org.bialydunajec.campedition.application.query.api

import org.bialydunajec.campedition.application.query.readmodel.CampEditionDomainModelReader
import org.bialydunajec.ddd.application.base.query.QueryProcessor

class CampEditionQueryGateway internal constructor(private val domainModelReader: CampEditionDomainModelReader) : QueryProcessor<CampEditionQuery> {

    override fun process(query: CampEditionQuery) = when (query) {
        is CampEditionQuery.All -> process(query)
        is CampEditionQuery.ById -> process(query)
    }

    fun process(query: CampEditionQuery.All) = domainModelReader.readFor(query)
    fun process(query: CampEditionQuery.ById) = domainModelReader.readFor(query)
}

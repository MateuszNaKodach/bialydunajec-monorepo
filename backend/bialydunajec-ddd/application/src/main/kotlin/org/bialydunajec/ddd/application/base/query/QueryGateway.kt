package org.bialydunajec.ddd.application.base.query

interface QueryGateway<QueryType: Query>{

    fun process(query: QueryType): Any?
}


package org.bialydunajec.ddd.application.base.query

interface QueryProcessor<QueryType: Query>{

    fun process(query: QueryType): Any?
}


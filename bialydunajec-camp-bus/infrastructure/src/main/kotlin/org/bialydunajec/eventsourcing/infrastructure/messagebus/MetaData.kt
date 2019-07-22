package org.bialydunajec.eventsourcing.infrastructure.messagebus

internal class MetaData private constructor(vararg pairs: Pair<String,Any>) : Map<String, Any> by mapOf<String, Any>(*pairs) {

    companion object {
        fun from(vararg pairs: Pair<String,Any>) = MetaData(*pairs)
    }
}


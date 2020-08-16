package org.bialydunajec.eventsourcing.infrastructure.messagebus

internal interface Message<PayloadType> {

    val identifier: String
    //val metaData: MetaData
    val payload: PayloadType
    val payloadType: Class<PayloadType>

}

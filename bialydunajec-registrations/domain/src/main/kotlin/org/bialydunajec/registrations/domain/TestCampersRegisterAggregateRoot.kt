package org.bialydunajec.registrations.domain

import java.util.*

data class TestCampersRegisterAggregateRoot(val id: String = UUID.randomUUID().toString()) {

    private val testEntity = TestInternalEntity()

    fun getTestEntity() = TestEntitySnapshot(testEntity.name)

}

class TestEntitySnapshot(val name: String)

internal class TestInternalEntity(val name: String = "EntityNameRepo")


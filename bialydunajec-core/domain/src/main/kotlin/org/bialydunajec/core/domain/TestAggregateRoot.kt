package org.bialydunajec.core.domain

import java.util.*

data class TestAggregateRoot(val id: String = UUID.randomUUID().toString()) {

    private val testEntity = TestInternalEntity()

    fun getTestEntity() = TestEntitySnapshot(testEntity.name)

}

class TestEntitySnapshot(val name: String)

internal class TestInternalEntity(val name: String = "EntityNameRepo")


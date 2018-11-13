package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSizeOptionSnapshot
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "camp_registrations")
class ShirtSizeOption(
        private var size: ShirtSize,
        private var available: Boolean = true
) : IdentifiedEntity<ShirtSizeOptionId> {

    @EmbeddedId
    override val entityId: ShirtSizeOptionId = ShirtSizeOptionId()

    fun update(size: ShirtSize, available: Boolean){
        this.size = size
        this.available = available
    }
    fun getSize() = size
    fun isAvailable() = available
    fun getSnapshot() = ShirtSizeOptionSnapshot(entityId, size, available)
}
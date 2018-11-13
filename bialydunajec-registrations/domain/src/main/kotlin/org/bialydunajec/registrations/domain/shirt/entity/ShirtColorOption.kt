package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtColorOptionSnapshot
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "camp_registrations")
class ShirtColorOption(
        private var color: Color,
        private var available: Boolean = true
) : IdentifiedEntity<ShirtColorOptionId> {

    @EmbeddedId
    override val entityId: ShirtColorOptionId = ShirtColorOptionId()

    fun update(color: Color, available: Boolean) {
        this.color = color
        this.available = available
    }
    fun getColor() = color
    fun isAvailable() = available
    fun getSnapshot() = ShirtColorOptionSnapshot(entityId, color, available)
}
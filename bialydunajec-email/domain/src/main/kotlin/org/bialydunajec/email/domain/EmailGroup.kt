package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.email.domain.valueobject.EmailGroupName
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(schema = "email_group")
class EmailGroup(
    emailGroupId: EmailGroupId,
    name: EmailGroupName? = null
) : AuditableAggregateRoot<EmailGroupId, EmailGroupEvent>(emailGroupId), Versioned {

    var name: EmailGroupName? = name
        private set

    init {
        registerEvent(
            EmailGroupEvent.EmailGroupCreated(
                getAggregateId(),
                name
            )
        )
    }

    fun changeName(newName: EmailGroupName) {
        if (this.name !== newName) {
            registerEvent(
                EmailGroupEvent.EmailGroupNameChanged(
                    getAggregateId(),
                    newName
                )
            )
        }
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version


}

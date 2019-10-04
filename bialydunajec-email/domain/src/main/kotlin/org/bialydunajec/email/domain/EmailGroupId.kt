package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

const val DEFAULT_EMAIL_GROUP_ID: String = "DEFAULT_GROUP"
const val LEVEL_DELIMITER: String = "+"

/**
 * EmailGroupId wyznacza także hierarchię poprzez specjalny format zapisu.
 * Przykładowo grupa CAMP-PARTICIPANT+EDITION_35+COTTAGE_12 jest bezpośrednim dzieckiem grupy CAMP-PARTICIPANT+EDITION_35
 */
@Embeddable
class EmailGroupId(emailGroupId: String = DEFAULT_EMAIL_GROUP_ID) : AggregateId(emailGroupId) {

    val levels = emailGroupId.split(LEVEL_DELIMITER).map { EmailGroupId(it) }

    fun isParentOf(emailGroupId: EmailGroupId) = levels.contains(emailGroupId.levels.first())

    fun newChild(emailGroupId: EmailGroupId) = EmailGroupId("$this$LEVEL_DELIMITER$emailGroupId")
}

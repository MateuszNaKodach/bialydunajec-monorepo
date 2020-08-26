package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

const val DEFAULT_EMAIL_GROUP_ID: String = "DEFAULT_GROUP"
const val LEVEL_DELIMITER: String = "+"

/**
 * EmailGroupId wyznacza także hierarchię poprzez specjalny format zapisu.
 * Przykładowo grupa CAMP-PARTICIPANT+EDITION_35+COTTAGE_12 jest bezpośrednim dzieckiem grupy CAMP-PARTICIPANT+EDITION_35
 */
@Embeddable
class EmailGroupId(emailGroupId: String = DEFAULT_EMAIL_GROUP_ID) : AggregateId(emailGroupId) {

    private val levels: List<EmailGroupId>
        get() = getIdentifierValue().split(LEVEL_DELIMITER).map { EmailGroupId(it) }

    internal fun isParentOf(emailGroupId: EmailGroupId) = emailGroupId.levels.containsAll(levels)

    internal fun newChild(emailGroupId: EmailGroupId) = EmailGroupId("$this$LEVEL_DELIMITER$emailGroupId")
}

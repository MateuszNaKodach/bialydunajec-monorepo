package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import javax.persistence.Embeddable

@Embeddable
data class CottageConditionsDescriptionItem(val title: String, val content: String, val iconUrl: Url)

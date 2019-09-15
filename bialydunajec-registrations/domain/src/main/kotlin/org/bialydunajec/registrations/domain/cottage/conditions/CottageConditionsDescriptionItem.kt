package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url

data class CottageConditionsDescriptionItem(val title: String, val content: String, val iconUrl: Url)

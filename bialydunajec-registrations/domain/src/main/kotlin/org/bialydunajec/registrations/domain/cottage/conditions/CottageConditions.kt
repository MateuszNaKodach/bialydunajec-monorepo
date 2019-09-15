package org.bialydunajec.registrations.domain.cottage.conditions

class CottageConditions {

    var items: List<CottageConditionsDescriptionItem> = listOf()
        private set


    fun update(newConditionsDescription: List<CottageConditionsDescriptionItem>) {
        items = newConditionsDescription
    }
}

package org.bialydunajec.registrations.domain.cottage.conditions

class CottageConditions {

    var conditionsDescription: String = ""
        private set


    fun update(newConditionsDescription: String) {
        conditionsDescription = newConditionsDescription
    }
}

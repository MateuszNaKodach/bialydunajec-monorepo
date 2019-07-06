package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class CampEditionDomainRule : DomainRule {
    CAMP_EDITION_TO_UPDATE_MUST_EXISTS,
    CAMP_EDITION_START_DATE_HAS_TO_BE_BEFORE_END_DATE {
        override fun getDescription() = "Dzień rozpoczęcia Edycji Obozu nie może być równy lub późniejszy dniowi zakończenia."
    },
    CAMP_EDITION_END_DATE_HAS_TO_BE_AFTER_START_DATE {
        override fun getDescription() = "Dzień zakończenia Edycji Obozu musi być późniejszy niż dzień rozpoczęcia."
    },
    DOWN_PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_TOTAL_PRICE {
        override fun getDescription() = "Kwota zadatku musi być mniejsza od całościowej ceny wyjazdu."
    };

    override fun getRuleName() = name
    override fun getDescription(): String? = null
}

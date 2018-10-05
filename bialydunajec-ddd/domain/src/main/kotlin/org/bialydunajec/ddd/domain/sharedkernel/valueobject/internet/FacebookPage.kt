package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
data class FacebookPage(
        @Lob
        @URL
        @NotBlank
        private val facebookPageUrl: String
) : ValueObject {

        override fun toString() = facebookPageUrl
        fun getUrl() = facebookPageUrl
}

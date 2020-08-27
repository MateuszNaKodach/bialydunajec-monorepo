package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
data class FacebookGroup(
        @Lob
        @URL
        @NotBlank
        private val facebookGroupUrl: String
) : ValueObject {

        override fun toString() = facebookGroupUrl
        fun getUrl() = facebookGroupUrl
}

package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
data class Instagram(
        @Lob
        @URL
        @NotBlank
        private val instagramUrl: String
) : ValueObject {

        override fun toString() = instagramUrl
        fun getUrl() = instagramUrl
}

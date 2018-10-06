package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
open class Url(
        @Lob
        @URL
        @NotBlank
        private val url: String
) : ValueObject {
    @Embeddable
    class InternalUrl(url: String) : Url(url)

    @Embeddable
    class ExternalUrl(url: String) : Url(url)

    override fun toString() = url
}
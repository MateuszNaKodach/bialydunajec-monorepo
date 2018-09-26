package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
open class Url(
        @Lob
        @URL
        @NotBlank
        val url: String
) {
    @Embeddable
    class InternalUrl(url: String) : Url(url)

    @Embeddable
    class ExternalUrl(url: String) : Url(url)
}

package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
sealed class Url {
    class InternalUrl(
            @URL
            @NotBlank
            val url: String) : Url()

    class ExternalUrl(
            @URL
            @NotBlank
            val url: String) : Url()
}

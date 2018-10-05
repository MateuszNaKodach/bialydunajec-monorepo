package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
data class ExtendedDescription(
        @Column(name = "description_title")
        val title: String?,

        @Lob
        @NotBlank
        @Column(name = "description_content")
        val content: String
)
package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Note(
        @Column(name = "note_title")
        val title: String?,

        @NotBlank
        @Column(name = "note_content")
        val content: String
)
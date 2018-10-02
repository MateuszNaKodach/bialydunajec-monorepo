package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

interface WithNotes<T> {
    fun addNote(note: Note): T
    fun getAllNotes(): Collection<Note>
}
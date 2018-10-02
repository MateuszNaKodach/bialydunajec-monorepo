package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

interface MutableWithSingleNote<T> {
    fun addNote(note: Note)
    fun getAllNotes(): Collection<Note>
}
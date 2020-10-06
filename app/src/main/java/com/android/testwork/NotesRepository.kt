package com.android.testwork

import androidx.lifecycle.LiveData


class NotesRepository(private val dao: DAO) {

    val allNotes: LiveData<List<Note>> = dao.getAllNotes()

    fun insertNote(note: Note) = dao.insertNote(note)

    fun deleteNote(note: Note) = dao.deleteNote(note)

    fun getNoteById(noteId: Int) = dao.getNoteById(noteId)

    fun updateNote(note: Note) = dao.updateNote(note)

}
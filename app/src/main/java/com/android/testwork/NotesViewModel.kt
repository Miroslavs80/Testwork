package com.android.testwork

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesViewModel(app: Application): AndroidViewModel(app) {

    private val repository: NotesRepository

    val allNotes: LiveData<List<Note>>

    init {
        val dao = NotesDataBase.getInstance(app).notesDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.insertNote(note) }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.deleteNote(note) }

    fun getNoteById(noteId: Int): Note = runBlocking(Dispatchers.IO) { repository.getNoteById(noteId) }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.updateNote(note) }


}
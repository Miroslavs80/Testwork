package com.android.testwork

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM Notes ORDER BY title desc")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Notes WHERE id = :noteId")
    fun getNoteById(noteId: Int): Note

    @Delete
    fun deleteNote(note: Note)


}
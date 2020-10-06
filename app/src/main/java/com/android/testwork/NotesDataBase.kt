package com.android.testwork

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {

    abstract fun notesDao(): DAO

    companion object {


        private var INSTANCE: NotesDataBase? = null

        fun getInstance(context: Context): NotesDataBase {
            val tempoInstance = INSTANCE

            if(tempoInstance != null) return tempoInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDataBase::class.java,
                    "NotesDataBase").build()
                INSTANCE = instance
                return instance
            }

        }

    }

}
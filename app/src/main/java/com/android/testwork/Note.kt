package com.android.testwork

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(var title: String = "", var noteText: String = "") {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
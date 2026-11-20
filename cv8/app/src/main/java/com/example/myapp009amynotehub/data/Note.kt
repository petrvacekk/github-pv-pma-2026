
package com.example.myapp009amynotehub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val content: String,

    // Automatický čas vytvoření poznámky
    val createdAt: Long = System.currentTimeMillis(),

    // Kategorie poznámky
    val category: String = "General"
)

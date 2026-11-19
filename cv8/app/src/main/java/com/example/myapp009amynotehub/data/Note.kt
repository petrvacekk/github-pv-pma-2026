package com.example.myapp009amynotehub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // ID poznámky, automaticky generované
    val title: String, // Název poznámky
    val content: String, // Obsah poznámky
    val priority: Int = 1 // Priorita poznámky (1 = Nízká, 2 = Střední, 3 = Vysoká)
)
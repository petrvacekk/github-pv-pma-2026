package com.example.myapp009amynotehub.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)

abstract class NoteHubDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
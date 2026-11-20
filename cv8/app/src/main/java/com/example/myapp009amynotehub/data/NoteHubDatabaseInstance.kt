package com.example.myapp009amynotehub.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object NoteHubDatabaseInstance {

    @Volatile
    private var INSTANCE: NoteHubDatabase? = null

    // Migrace databáze z verze 1 na verzi 2
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE note_table ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE note_table ADD COLUMN category TEXT NOT NULL DEFAULT 'General'")
        }
    }

    // Migrace databáze z verze 2 na verzi 3
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE note_table ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE note_table ADD COLUMN category TEXT NOT NULL DEFAULT 'General'")
        }
    }

    fun getDatabase(context: Context): NoteHubDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteHubDatabase::class.java,
                "notehub_database"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .fallbackToDestructiveMigration(true)
                .build()

            INSTANCE = instance
            instance
        }
    }
}
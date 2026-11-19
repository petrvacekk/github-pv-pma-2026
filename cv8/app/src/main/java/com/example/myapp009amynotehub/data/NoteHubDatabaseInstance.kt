package com.example.myapp009amynotehub.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object NoteHubDatabaseInstance {

    @Volatile
    private var INSTANCE: NoteHubDatabase? = null

    // Definuje migraci ze verze 1 na verzi 2 – přidání nového sloupce priority
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE note_table ADD COLUMN priority INTEGER NOT NULL DEFAULT 1")
        }
    }

    /**
     * Vrátí instanci databáze. Pokud ještě neexistuje, vytvoří ji.
     */
    fun getDatabase(context: Context): NoteHubDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteHubDatabase::class.java,
                "notehub_database"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .build()
            INSTANCE = instance
            instance
        }
    }

}
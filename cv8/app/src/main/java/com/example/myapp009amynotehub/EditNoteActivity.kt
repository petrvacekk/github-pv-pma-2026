package com.example.myapp009amynotehub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp009amynotehub.data.Note
import com.example.myapp009amynotehub.data.NoteDao
import com.example.myapp009amynotehub.data.NoteHubDatabaseInstance
import com.example.myapp009amynotehub.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var noteDao: NoteDao
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDao = NoteHubDatabaseInstance.getDatabase(this).noteDao()

        // Získáme ID poznámky z Intentu
        noteId = intent.getIntExtra("note_id", -1)
        binding.tvNoteId.text = "ID poznámky: $noteId"

        // Načteme poznámku z DB
        lifecycleScope.launch {
            noteDao.getAllNotes().collect { notes ->
                val note = notes.find { it.id == noteId }
                if (note != null) {
                    binding.etEditTitle.setText(note.title)
                    binding.etEditContent.setText(note.content)
                    binding.etEditPriority.setText(note.priority.toString())
                }
            }
        }

        // Kliknutí na Uložit
        binding.btnSaveChanges.setOnClickListener {
            val updatedTitle = binding.etEditTitle.text.toString()
            val updatedContent = binding.etEditContent.text.toString()
            val updatedPriority = binding.etEditPriority.text.toString().toIntOrNull() ?: 1

            val updatedNote = Note(
                id = noteId,
                title = updatedTitle,
                content = updatedContent,
                priority = updatedPriority
            )

            lifecycleScope.launch(Dispatchers.IO) {
                noteDao.update(updatedNote)
                finish()
            }
        }

    }
}
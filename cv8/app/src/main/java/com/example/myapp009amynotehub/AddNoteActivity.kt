package com.example.myapp009amynotehub

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp009amynotehub.data.Note
import com.example.myapp009amynotehub.data.NoteHubDatabaseInstance
import com.example.myapp009amynotehub.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = NoteHubDatabaseInstance.getDatabase(this)
        val noteDao = db.noteDao()

        // Naplnění kategorií pro Spinner
        val categories = arrayOf("Obecné", "Osobní", "Práce", "Škola")
        val spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategory.adapter = spinnerAdapter

        // Formátování a zobrazení dnešního data
        val createdAtTimestamp = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        binding.tvCreatedDate.text = "Datum vytvoření: ${dateFormat.format(Date(createdAtTimestamp))}"

        binding.btnSaveNote.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()
            val selectedCategory = binding.spinnerCategory.selectedItem?.toString() ?: "Obecné"

            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(
                    this,
                    "Vyplň název i obsah",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val note = Note(
                title = title,
                content = content,
                createdAt = createdAtTimestamp,
                category = selectedCategory
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    noteDao.insert(note)
                }

                finish()
            }
        }
    }
}
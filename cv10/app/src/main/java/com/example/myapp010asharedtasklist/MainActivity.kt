package com.example.myapp010asharedtasklist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp010asharedtasklist.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TaskAdapter

    private val collectionName = "tasks_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        // Nastavení adapteru
        adapter = TaskAdapter(
            tasks = emptyList(),
            onChecked = { task -> toggleCompleted(task) },
            onDelete = { task -> deleteTask(task) }
        )

        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Přidání úkolu
        binding.buttonAdd.setOnClickListener {
            val title = binding.inputTask.text.toString().trim()
            if (title.isNotEmpty()) {
                addTask(title)
                binding.inputTask.text.clear()
            }
        }

        // Realtime sledování Firestore
        listenForTasks()
    }

    private fun addTask(title: String) {
        val task = Task(title = title, completed = false)
        db.collection(collectionName).add(task)
    }

    private fun toggleCompleted(task: Task) {
        // Vyhledá v databázi všechny dokumenty, které mají stejné title jako kliknutý úkol
        db.collection(collectionName)
            .whereEqualTo("title", task.title)
            .get()
            .addOnSuccessListener { docs ->
                // Pro každý nalezený dokument změní hodnotu "completed" na opačnou
                for (doc in docs) {
                    db.collection(collectionName)
                        .document(doc.id)
                        .update("completed", !task.completed)
                }
            }
    }

    private fun deleteTask(task: Task) {
        db.collection(collectionName)
            .whereEqualTo("title", task.title)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    db.collection(collectionName)
                        .document(doc.id)
                        .delete()
                }
            }
    }

    private fun listenForTasks() {
        db.collection(collectionName)
            // Sleduje kolekci v reálném čase
            .addSnapshotListener { snapshots, _ ->
                // Převede dokumenty z Firestore na seznam objektů Task
                val taskList = snapshots?.toObjects(Task::class.java) ?: emptyList()
                // Aktualizuje RecyclerView novým seznamem úkolů

                adapter.submitList(taskList)
            }
    }
}
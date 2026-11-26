package com.example.myapp010asharedtasklist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapp010asharedtasklist.databinding.ItemTaskBinding

// Adapter obsluhuje zobrazení seznamu úkolů v RecyclerView.
// Přijímá list úkolů a callbacky pro změnu stavu a smazání.
class TaskAdapter(
    private var tasks: List<Task>,
    private val onChecked: (Task) -> Unit,  // zavolá se při kliknutí na checkbox
    private val onDelete: (Task) -> Unit     // zavolá se při kliknutí na ikonu smazání
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder drží jeden řádek seznamu (item_task.xml) a jeho view binding.
    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Vytvoření nového ViewHolderu – vytvoří se layout jednoho řádku.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    // Naplnění dat pro jeden řádek – nastavíme text, checkbox a tlačítko delete.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Nastaví text úkolu
        holder.binding.textTitle.text = task.title

        // 1. Nejprve odpojíme starý listener, aby se nespustil při programové změně
        holder.binding.checkCompleted.setOnCheckedChangeListener(null)

        // 2. Nastaví zaškrtnutí checkboxu podle hodnoty v objektu
        holder.binding.checkCompleted.isChecked = task.completed

        // 3. Teprve teď připojíme listener pro změnu stavu checkboxu uživatelem
        holder.binding.checkCompleted.setOnCheckedChangeListener { _, _ ->
            onChecked(task)
        }

        // Listener pro smazání úkolu
        holder.binding.imageDelete.setOnClickListener {
            onDelete(task)
        }
    }

    // Počet položek v seznamu
    override fun getItemCount() = tasks.size

    // Aktualizace seznamu – nahradí starý list novým a překreslí RecyclerView
    fun submitList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}
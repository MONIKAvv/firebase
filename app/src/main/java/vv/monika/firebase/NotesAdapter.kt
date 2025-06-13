package vv.monika.firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vv.monika.firebase.databinding.NotesitemsBinding

class NotesAdapter(private val notes: List<NotesItem>, private val itemClickListener: AllNotesActivity) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
        interface OnItemClickListerner{
            fun onDeleteClick(noteId:String)
            fun onUpdateClick(noteId:String , title:String, description: String)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesitemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: NotesItem = notes[position]
        holder.bind(note)
        holder.binding.updateBtn.setOnClickListener {
            itemClickListener.onUpdateClick(note.noteId, note.title, note.description)
        }
        holder.binding.deleteBtn.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(val binding: NotesitemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NotesItem) {
            binding.titleTextView.text = note.title
            binding.descriptionTextView.text = note.description
        }


    }
}
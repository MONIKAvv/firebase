package vv.monika.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vv.monika.firebase.databinding.ActivityAllNotesBinding
import vv.monika.firebase.databinding.DialogUpdateNotesBinding

class AllNotesActivity : AppCompatActivity(), NotesAdapter.OnItemClickListerner {
    private val binding: ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        recyclerView = binding.notesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

//initialise firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteReference: DatabaseReference =
                databaseReference.child("users").child(user.uid).child("notes")
            noteReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<NotesItem>()
                    for (noteSnapshot: DataSnapshot in snapshot.children) {
                        val note: NotesItem? = noteSnapshot.getValue(NotesItem::class.java)
                        note?.let {
                            notesList.add(it)
                        }
                    }
                    notesList.reverse()
                    val adapter = NotesAdapter(notesList, this@AllNotesActivity)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

    }

    override fun onDeleteClick(noteId: String) {

        val currentUser: FirebaseUser? = auth.currentUser
        currentUser?.let { user ->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            noteReference.child(noteId).removeValue()
        }
    }

    override fun onUpdateClick(noteId: String, currentTitle: String, currentDescription: String) {

        val dialogBinding: DialogUpdateNotesBinding = DialogUpdateNotesBinding.inflate(
            LayoutInflater.from(this)
        )
        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Notes")
            .setPositiveButton("Update") { dialog, _ ->
                val newTitle: String = dialogBinding.updateTitleEdittext.text.toString()
                val newDescription: String = dialogBinding.updateDescriptionEdittext.text.toString()
                updateNotesDatabase(noteId, newTitle, newDescription)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitleEdittext.setText(currentTitle)
        dialogBinding.updateDescriptionEdittext.setText(currentDescription)

        dialog.show()
    }

    private fun updateNotesDatabase(noteId: String, newTitle: String, newDescription: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteReference =
                user.let { databaseReference.child("users").child(user.uid).child("notes") }
            val updateNotes = NotesItem(newTitle, newDescription, noteId)
            noteReference.child(noteId).setValue(updateNotes)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Note updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "failed to update note", Toast.LENGTH_SHORT).show()
                    }

                }

        }
    }
}
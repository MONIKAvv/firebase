package vv.monika.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.AtomicReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vv.monika.firebase.databinding.ActivityAddNewNotesBinding

class AddNewNotesActivity : AppCompatActivity() {
    private val binding: ActivityAddNewNotesBinding by lazy {
        ActivityAddNewNotesBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
//        user releated any work
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        initialisation firebase databse reference
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.saveNotesBtn.setOnClickListener {
//            get text from edit text
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()

            if (title.isEmpty() && description.isEmpty()) {
                Toast.makeText(this, "Please Fill both fields", Toast.LENGTH_SHORT).show()
            } else {
//                save data
//                its good practice to create dataclass to store variables in firebase
                val currentUser: FirebaseUser? = auth.currentUser
                currentUser?.let { user ->
//                    generates unique keys for each notes
                    val noteskey =
                        databaseReference.child("users").child(user.uid).child("Notes").push().key
//                    note item instance
                    val notesItem = NotesItem(title, description, noteskey?: "")
                    if (noteskey != null) {
//                        add notes to the user note
                        databaseReference.child("users").child(user.uid).child("notes")
                            .child(noteskey).setValue(notesItem)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Note save Successful", Toast.LENGTH_SHORT)
                                        .show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to save Note", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }

                }

            }

        }

    }
}
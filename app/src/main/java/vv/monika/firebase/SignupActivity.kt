package vv.monika.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import vv.monika.firebase.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//initialise firebase auth
        auth = FirebaseAuth.getInstance()

        binding.signInBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.registerBtn.setOnClickListener {
//            get text from edit text field
            val email = binding.signupemail.text.toString()
            val username = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            val repeat_password = binding.signupRptPassword.text.toString()

//          check any field is empty
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || repeat_password.isEmpty()) {
                Toast.makeText(this, "Please Fill all required fields", Toast.LENGTH_SHORT).show()
            } else if (password != repeat_password) {
                Toast.makeText(this, "Repeat Password not matched", Toast.LENGTH_SHORT).show()
            } else {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this){
            task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
            }


        }

    }
}
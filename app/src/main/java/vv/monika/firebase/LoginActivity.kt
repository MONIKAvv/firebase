package vv.monika.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import vv.monika.firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by lazy {
ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onStart() {
        super.onStart()
//        check if user already login
        val currentUser : FirebaseUser? = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

//        initialise firebase auth
        auth = FirebaseAuth.getInstance()
//

        binding.logInBtn.setOnClickListener {
            val userName = binding.username.text.toString()
            val passWord = binding.password.text.toString()

            if (userName.isEmpty()||passWord.isEmpty()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(userName, passWord)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Sign-In failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}




//if we want to sign-out , we can use this code
//AuthUI.getInstance()
//.signOut(this)
//.addOnCompleteListener {
//    // ...
//}
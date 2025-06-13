package vv.monika.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vv.monika.firebase.databinding.ActivityMainBinding
import vv.monika.firebase.ui.theme.FirebaseTheme

class MainActivity : ComponentActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createNewNotesBtn.setOnClickListener {
            startActivity(Intent(this, AddNewNotesActivity::class.java))
        }
        binding.openAllNotesBtn.setOnClickListener {
            startActivity(Intent(this, AllNotesActivity::class.java))
        }

    }
}



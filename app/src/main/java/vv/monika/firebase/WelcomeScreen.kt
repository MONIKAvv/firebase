package vv.monika.firebase

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils

import vv.monika.firebase.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {
    private val binding  : ActivityWelcomeScreenBinding by lazy {
        ActivityWelcomeScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

//        val mediaPlayer = MediaPlayer.create(this,R.raw.welcome)
//        mediaPlayer.start()
//        mediaPlayer.stop()

        Handler(Looper.getMainLooper()).postDelayed({

         startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)

        val welcomeText = "WELCOME"
        val spannableString = SpannableString(welcomeText)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")), 0,5,0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#312222")), 5,welcomeText.length,0)

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.welcomeText.startAnimation(fadein)

        binding.welcomeText.text = spannableString



    }
}
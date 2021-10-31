package com.simranjeetsingh05.viakhat.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ebanx.swipebtn.OnStateChangeListener
import com.simranjeetsingh05.viakhat.databinding.ActivitySplashScreenBinding
import kotlin.math.max


class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.swipeButton.setOnStateChangeListener(OnStateChangeListener() {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            setCircularRevealActivity()
        })
    }
        private fun setCircularRevealActivity(){

            val intent = Intent(this@SplashScreenActivity,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


}
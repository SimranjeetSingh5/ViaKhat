package com.simranjeetsingh05.viakhat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simranjeetsingh05.viakhat.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
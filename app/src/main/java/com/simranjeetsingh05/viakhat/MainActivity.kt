package com.simranjeetsingh05.viakhat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simranjeetsingh05.viakhat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
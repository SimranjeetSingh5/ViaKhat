package com.simranjeetsingh05.viakhat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simranjeetsingh05.viakhat.databinding.ActivityUsersBinding

class UsersActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
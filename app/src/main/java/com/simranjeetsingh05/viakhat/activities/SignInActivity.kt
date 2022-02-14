package com.simranjeetsingh05.viakhat.activities

import android.app.Activity
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import com.simranjeetsingh05.viakhat.databinding.ActivitySignInBinding
import com.simranjeetsingh05.viakhat.databinding.WelcomeDialogBinding
import kotlin.math.max

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            init()
        }
        if (savedInstanceState == null) {
            binding.root.visibility = View.INVISIBLE
            val viewTreeObserver: ViewTreeObserver = binding.root.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        circularRevealActivity()
                        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }
    private  fun circularRevealActivity() {
        val cx: Int = binding.root.right/2
        val cy: Int = binding.root.bottom
        val finalRadius: Int = max(binding.root.width, binding.root.height)
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                binding.root,
                cx,
                cy, 0f,
                finalRadius.toFloat())
        circularReveal.duration = 1000
        binding.root.visibility = View.VISIBLE


        val alert = ViewDialog()
        alert.welcomeDialog(this)
        circularReveal.start()

    }
    class ViewDialog {
     fun welcomeDialog(activity: Activity?) {
        val welcomeBinding = WelcomeDialogBinding.inflate(activity!!.layoutInflater)

        val dialog = Dialog(activity)
        dialog.setContentView(welcomeBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))

        dialog.show()

        welcomeBinding.clickToContinueButton.setOnClickListener {
            dialog.cancel() }
    }}
    private fun init(){
        binding.signUpButton.setOnClickListener {

            val intent = Intent(applicationContext, SignUpActivity::class.java)

            val b: Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, b)
        }

    }





}









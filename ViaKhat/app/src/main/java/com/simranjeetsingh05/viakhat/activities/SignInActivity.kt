package com.simranjeetsingh05.viakhat.activities

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.simranjeetsingh05.viakhat.databinding.ActivitySignInBinding
import com.simranjeetsingh05.viakhat.databinding.WelcomeDialogBinding
import com.simranjeetsingh05.viakhat.utilities.Constants
import com.simranjeetsingh05.viakhat.utilities.PreferenceManager
import java.util.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var preferenceManager:PreferenceManager
    private var welcomeDialogBoolean:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGN_IN)){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setListeners()

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
        val finalRadius: Int = Math.max(binding.root.width, binding.root.height)
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                binding.root,
                cx,
                cy, 0f,
                finalRadius.toFloat())
        circularReveal.duration = 1000
        binding.root.visibility = View.VISIBLE

        welcomeDialog()
        circularReveal.start()

    }

    private  fun welcomeDialog() {

        val welcomeBinding = WelcomeDialogBinding.inflate(layoutInflater)

        val dialog = Dialog(this@SignInActivity)
        dialog.setContentView(welcomeBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))

        dialog.show()

        welcomeBinding.clickToContinueButton.setOnClickListener {
            dialog.cancel() }
    }
    private fun setListeners(){
        binding.signUpButton.setOnClickListener {

            val intent = Intent(this@SignInActivity,SignUpActivity::class.java)

            val b: Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, b)
        }
        binding.signinButton.setOnClickListener {
//            addDataToFirestore()
            if(isValidSignIn()){
                signIn()
            }
        }

    }
    private fun signIn(){
        loading(true)
        val database:FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .whereEqualTo(Constants.KEY_EMAIL,binding.email.text.toString())
            .whereEqualTo(Constants.Key_PASSWORD,binding.password.text.toString())
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful && it.result != null && it.result!!.documents.size > 0 ){
                    val documentSnapshot:DocumentSnapshot = it.result!!.documents[0]
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGN_IN,true)
                    preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.id)
                    preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME))
                    preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE))
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK+Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else{
                    loading(false)
                    showToast("Unable to sign in")
                }
            }


    }
    private fun loading(isLoading:Boolean){
        if(isLoading){
            binding.signinButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.signinButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
        private fun showToast(message:String){
            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()

        }
        private fun isValidSignIn():Boolean{
            return if(binding.email.text.toString().trim().isEmpty()){
                showToast("Enter email")
                false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()){
                showToast("Enter valid email")
                false
            }else if(binding.password.text.toString().trim().isEmpty()){
                showToast("Enter password")
                false
            }else{
                true
            }
        }

    }
















































//    private fun addDataToFirestore() {
//        val database = FirebaseFirestore.getInstance()
//        val data: HashMap<String, String> = HashMap()
//        data["first_name"] = "Simranjeet"
//        data["last_name"] = "Singh"
//        database.collection("users")
//            .add(data)
//            .addOnSuccessListener {
//                Toast.makeText(
//                    applicationContext,
//                    "Data inserted",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(
//                    applicationContext,
//                    e.message,
//                    Toast.LENGTH_SHORT
//                ).show() }
//    }









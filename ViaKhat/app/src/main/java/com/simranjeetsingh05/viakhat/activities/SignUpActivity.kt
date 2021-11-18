package com.simranjeetsingh05.viakhat.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.simranjeetsingh05.viakhat.databinding.ActivitySignUpBinding
import com.simranjeetsingh05.viakhat.utilities.Constants
import com.simranjeetsingh05.viakhat.utilities.PreferenceManager
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private var encodedImage:String? = null
    private lateinit var preferenceManager:PreferenceManager
    private var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        setListeners()
    }
    private fun setListeners(){
        binding.submitSignUpButton.setOnClickListener {
            if(isValidSignUpDetails()){
                signUp()
            }
        }
        binding.alreadyLogInTV.setOnClickListener { onBackPressed() }
        binding.dobButton.setOnClickListener { calenderSet() }
        binding.layoutImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags((Intent.FLAG_GRANT_READ_URI_PERMISSION))
            pickImage.launch(intent)
        }
    }
    private fun showToast(message:String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    private fun signUp(){
        loading(true)
        val database = FirebaseFirestore.getInstance()
        val user = HashMap<String,Any>()
        user[Constants.KEY_NAME] = binding.nameET.text.toString()
        user[Constants.KEY_EMAIL] = binding.emailET.text.toString()
        user[Constants.Key_PASSWORD] = binding.passwordET.text.toString()
        user[Constants.KEY_IMAGE] = encodedImage.toString()
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener{
                    loading(false)
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGN_IN,true)
                    preferenceManager.putString(Constants.KEY_USER_ID,it.id)
                    preferenceManager.putString(Constants.KEY_NAME,binding.nameET.text.toString())
                    preferenceManager.putString(Constants.KEY_IMAGE,encodedImage)
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK+Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)

                }
                .addOnFailureListener{
                    loading(false)
                    showToast(it.message.toString())

                }
    }

    private fun encodeImage(bitmap: Bitmap):String{
        val previewWidth =  150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val byteArrayOutputStream =  ByteArrayOutputStream()
        val previewBitmap:Bitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false)
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes:ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private var pickImage:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { result ->
        run {
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val imageUri: Uri = result.data!!.data!!
                    try {
                        val inputStream = contentResolver.openInputStream(imageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imageProfile.setImageBitmap(bitmap)
                        binding.addImageTV.visibility = View.GONE
                        encodedImage = encodeImage(bitmap)

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun isValidSignUpDetails():Boolean{
        if(encodedImage == null){
            showToast("Select profile image")
            return false
        }else if(binding.nameET.text.toString().trim().isEmpty()){
            showToast("Enter Name")
            return false
        }else if(binding.emailET.text.toString().trim().isEmpty()) {
            showToast("Enter Email")
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailET.text.toString()).matches()) {
            showToast("Enter Valid email")
            return false
        }else if(binding.dobButton.text.toString().trim().isEmpty()) {
            showToast("Select date of birth")
            return false
        } else if(binding.passwordET.text.toString().trim().isEmpty()) {
            showToast("Enter password")
            return false
        }else if(binding.confirmPasswordET.text.toString().trim().isEmpty()) {
            showToast("Confirm your password")
            return false
        }else if(binding.passwordET.text.toString() != binding.confirmPasswordET.text.toString()) {
            showToast("Password and Confirm password does not match")
            return false
        }else{
            return true
        }
    }
    private fun calenderSet(){
        // create an OnDateSetListener
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        binding.dobButton.setOnClickListener {
            DatePickerDialog(this@SignUpActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dobButton.text = sdf.format(cal.time)
    }
    private fun loading(isloading:Boolean){
        if(isloading){
            binding.submitSignUpButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.submitSignUpButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE

        }
    }
}
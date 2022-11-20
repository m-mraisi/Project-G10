package com.test.project_g10

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.project_g10.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {

    lateinit var binding:ActivityNameBinding
    var TAG = this@NameActivity.toString()
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        sharedPrefs = this.getSharedPreferences("com_test_g10_PREFS_LESSONS", MODE_PRIVATE)

        binding.btnSaveName.setOnClickListener {
            val nameFromNameScreen = binding.edtName.text.toString()

            val isValidName = validateUsername(nameFromNameScreen)

            if(isValidName){

                addUsernameToPref(nameFromNameScreen)
                val intent = Intent(this, LessonsListActivity::class.java)
                startActivity(intent)
            }
            else{
                binding.edtName.error = "Please insert a valid name"
            }


        }

        Log.d(TAG, "onStart:  pressed")
        if(checkIfNameExists()){
            val intent = Intent(this, WelcomeScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateUsername(name:String):Boolean {
        if(name.isBlank()){
            return false

        }
        for (letter in name){
            if(!letter.isLetter()){
                return false
            }
        }
        return true
    }

    private fun addUsernameToPref(username:String) {
        with(sharedPrefs.edit()) {
            // write to sharedPreferences
            putString("USERNAME", username) // key value pair
            apply() // async action
        }
    }

    private fun checkIfNameExists():Boolean {
        return if (sharedPrefs.contains("USERNAME")) {
            val username = sharedPrefs.getString("USERNAME", "")
            username?.isNotBlank() ?: false

        } else{
            false
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity();
    }
}
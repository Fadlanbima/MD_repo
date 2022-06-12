package com.bangkit.getguide.preference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bangkit.getguide.HomeActivity
import com.bangkit.getguide.databinding.ActivityPreference2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Preference2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPreference2Binding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreference2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.button1.setOnClickListener {
            updatePreference(1)

            toHome()
        }

        binding.button2.setOnClickListener {
            updatePreference(2)

            toHome()
        }

        binding.button3.setOnClickListener {
            updatePreference(3)

            toHome()
        }

        binding.button4.setOnClickListener {
            updatePreference(4)

            toHome()
        }
    }

    private fun updatePreference(num: Int){
        val user = auth.currentUser
        val db = Firebase.firestore

        db.collection("users").document("${user?.uid.toString()}")
            .update("preference", num)
            .addOnSuccessListener { Log.d(TAG, "User preference successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating preference", e) }
    }

    private fun toHome(){
        val intent = Intent(this@Preference2Activity, HomeActivity::class.java)
        startActivity(intent)
    }

    companion object{
        val TAG = "Preference2 Activity"
    }
}
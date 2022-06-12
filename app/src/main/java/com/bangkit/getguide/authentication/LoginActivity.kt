package com.bangkit.getguide.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.bangkit.getguide.HomeActivity
import com.bangkit.getguide.utils.SessionManager
import com.bangkit.getguide.databinding.ActivityLoginBinding
import com.bangkit.getguide.preference.Preference2Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            showLoading(true)

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            //Validasi email
            if (email.isEmpty()) {
                binding.edtEmail.error = "Email Harus Diisi"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Email Tidak Valid"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.edtPassword.error = "Password Harus Diisi"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        val Session = SessionManager(this)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showLoading(false)

                    Session.Login()

                    if (checkPreference() == 0) {
                        val intent = Intent(this, Preference2Activity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkPreference(): Int {
        val user = auth.currentUser
        val db = Firebase.firestore
        var pref = 0

        val userData = db.collection("users").document("${user?.uid}")
        userData.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "User data: ${document.data}")
                    pref = document.getLong("preference")!!.toInt()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return pref
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = "Login Activity"
    }
}
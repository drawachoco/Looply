package com.example.looply

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginText: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // Inisialisasi FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Inisialisasi View
        nameEditText = findViewById(R.id.nameSigninEditText)
        emailEditText = findViewById(R.id.emailSigninEditText)
        passwordEditText = findViewById(R.id.passwordSigninEditText)
        signupButton = findViewById(R.id.signupButton)
        loginText = findViewById(R.id.loginText)

        // Set Listener pada Tombol Sign Up
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validasi Input
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi Format Email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pendaftaran ke Firebase
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Kirim Email Verifikasi
                        val user = firebaseAuth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    saveUserToDatabase(name, email)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Sign Up failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Navigasi ke Login Activity
        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveUserToDatabase(name: String, email: String) {
        val database = FirebaseDatabase.getInstance(
            "https://looply-v1-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference("users")
        val userId = firebaseAuth.currentUser?.uid ?: ""

        val user = mapOf(
            "name" to name,
            "email" to email
        )

        database.child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Sign Up Successful! Please check your email to verify your account.",
                        Toast.LENGTH_LONG
                    ).show()
                    // Navigasi ke Login Activity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to save user: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

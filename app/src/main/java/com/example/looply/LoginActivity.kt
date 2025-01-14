package com.example.looply

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupTextLink: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Menghubungkan elemen UI
        emailEditText = findViewById(R.id.usernameLoginEditText)
        passwordEditText = findViewById(R.id.passwordLoginEditText)
        loginButton = findViewById(R.id.loginButton)
        signupTextLink = findViewById(R.id.signupTextLink)

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Logika untuk tombol Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validasi input (cek apakah email dan password kosong)
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi format email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses login menggunakan Firebase Authentication
            loginWithEmailAndPassword(email, password)
        }

        // Logika untuk pindah ke SignUp Activity
        signupTextLink.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    // Cek apakah email sudah diverifikasi
                    if (user != null && user.isEmailVerified) {
                        // Login berhasil
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Pindah ke Activity berikutnya (misalnya WelcomeActivity)
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    } else {
                        // Email belum diverifikasi
                        Toast.makeText(
                            this,
                            "Please verify your email before logging in.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Login gagal
                    val exception = task.exception
                    Log.e("Login", "Login failed", exception)
                    Toast.makeText(this, "Login failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
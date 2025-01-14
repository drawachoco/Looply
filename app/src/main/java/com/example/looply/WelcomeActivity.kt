package com.example.looply

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Untuk mendukung tampilan layar penuh
        setContentView(R.layout.activity_welcome)

        // Menangani padding sistem untuk mendukung tampilan full screen
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcome)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol 'I'm In!' untuk melanjutkan ke MainActivity2
        findViewById<Button>(R.id.button4).setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left) // Animasi transisi
            finish() // Menutup WelcomeActivity setelah pindah
        }
    }
}

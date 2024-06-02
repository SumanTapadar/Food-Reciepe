package com.example.nowweather

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sigin_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val create=findViewById<TextView>(R.id.create_account)
        create.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        val emailInput = findViewById<TextInputEditText>(R.id.email)
        val passwordInput = findViewById<TextInputEditText>(R.id.pass3)
        val signInButton = findViewById<Button>(R.id.signin)

        signInButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userId = email.replace(".", "_")
                readData(userId, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(userId: String, password: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                val storedPassword = it.child("password").value.toString()
                if (storedPassword == password) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userId", userId)
                    Toast.makeText(this, "Signed in successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to sign in!", Toast.LENGTH_SHORT).show()
        }

    }
}

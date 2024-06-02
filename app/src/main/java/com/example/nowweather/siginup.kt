package com.example.nowweather

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_siginup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registerButton = findViewById<Button>(R.id.register)
        val agreeCheckBox = findViewById<CheckBox>(R.id.check)
        val signInTextView = findViewById<TextView>(R.id.signuppage)

        signInTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            if (agreeCheckBox.isChecked) {
                val nameInput = findViewById<TextInputEditText>(R.id.name)
                val emailInput = findViewById<TextInputEditText>(R.id.gmail)
                val passwordInput = findViewById<TextInputEditText>(R.id.pass)
                val name = nameInput.text.toString()
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                val user = User(name, email, password)
                database = FirebaseDatabase.getInstance().getReference("users")
                val userId = email.replace(".", "_")
                database.child(userId).setValue(user).addOnSuccessListener {
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
                    nameInput.text?.clear()
                    emailInput.text?.clear()
                    passwordInput.text?.clear()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.nowweather

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class account_page : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val a_name = findViewById<TextView>(R.id.acount_name)
        val a_gmail = findViewById<TextView>(R.id.acount_gmail)
        val a_password=findViewById<TextView>(R.id.acount_password)
        val a_top = findViewById<ImageView>(R.id.acount_top)

        val pass = intent.getStringExtra("userID2")

        if (pass != null) {
            database = FirebaseDatabase.getInstance().getReference("users")
            database.child(pass).get().addOnSuccessListener {
                if (it.exists()) {
                    val name = it.child("name").value
                    val gmail = it.child("email").value
                    val password=it.child("password").value
                    a_name.text = name.toString()
                    a_gmail.text = gmail.toString()
                    a_password.text=password.toString()
                } else {
                    Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No password provided", Toast.LENGTH_SHORT).show()
        }

        a_top.setOnClickListener {
            finish()
        }
    }
}

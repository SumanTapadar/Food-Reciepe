package com.example.nowweather

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class order_page : AppCompatActivity() {
    val chanel_id="chanelId"
    val chanel_name="chanelName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotification()

        setContentView(R.layout.activity_order_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val Image3=findViewById<ShapeableImageView>(R.id.lastimage)
        val name3=findViewById<TextView>(R.id.lastname)

        val check=findViewById<CheckBox>(R.id.checkBox)
        val oreder=findViewById<Button>(R.id.orderButton)

        val im=intent.getStringExtra("image")

        val na=intent.getStringExtra("name")
        Picasso.get().load(im).into(Image3)
        name3.text=na.toString()


val topLast=findViewById<ImageView>(R.id.toparrow3)
        topLast.setOnClickListener {
            finish()
        }


        oreder.setOnClickListener {
            if (check.isChecked) {
                showNotification()
                val intent2 = Intent(this, orderplaced::class.java)
                startActivity(intent2)
            } else {
                Toast.makeText(this, "Please check the checkbox to proceed", Toast.LENGTH_SHORT).show()
            }
        }




    }
    private fun createNotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance=NotificationManager.IMPORTANCE_DEFAULT
            val chanel=NotificationChannel(chanel_id,chanel_name,importance).apply {
                description="chanel description"
            }
            val notificationManager:NotificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(chanel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(){
        val builder=NotificationCompat.Builder(this,chanel_id)
            .setSmallIcon(R.drawable.notification)
            .setContentText("Congratulation...! Suman \n your order  is confirmed.\n Hope you enjoy this app service")
            .setContentTitle("Order Details :")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(1,builder.build())
        }
    }

}
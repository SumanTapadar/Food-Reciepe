package com.example.nowweather

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var userId: String
    lateinit var userID2:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userId = intent.getStringExtra("userId").toString()
        userID2=userId

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        sharedPref.edit().putString("userId", userId).apply()


        val accountButton = findViewById<ImageButton>(R.id.account_id)
        accountButton.setOnClickListener {
            val intent = Intent(this, account_page::class.java)
            intent.putExtra("userID2", userID2)
            startActivity(intent)
        }
        val cartButton=findViewById<ImageButton>(R.id.cart_id)
        cartButton.setOnClickListener {
            val intent=Intent(this,cart_page::class.java)
            startActivity(intent)
        }






        myRecyclerView = findViewById(R.id.recycle)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tasty.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)
        val service = retrofitBuilder.getdata(0, 10)
        service.enqueue(object : Callback<Apidata?> {
            override fun onResponse(call: Call<Apidata?>, response: Response<Apidata?>) {
                val data = response.body()?.results!!
                myAdapter = MyAdapter(this@MainActivity, data)
                myRecyclerView.adapter = myAdapter
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                Log.d("onResponse:", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<Apidata?>, t: Throwable) {
                Log.d("onFailure: ", "onFailure: " + t.message)
            }
        })

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure...?")
        builder.setIcon(R.drawable.exit1)
        builder.setMessage("You want to exit...!")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent=Intent(this,setting_page::class.java)
                startActivity(intent)

            }
            R.id.log -> {
                Toast.makeText(this, "You are loged out", Toast.LENGTH_SHORT)
                    .show()
                    val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.help -> {

                val intent=Intent(this,help_page::class.java)
                startActivity(intent)
            }
            R.id.wishlist -> {
                Toast.makeText(this, "Opening wishlist...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WishlistActivity::class.java)
                intent.putExtra("userID2", userID2)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.example.nowweather

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var myRecyclerView2: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        userId = sharedPref.getString("userId", "") ?: ""



        val image1 = findViewById<ImageView>(R.id.Image)
        val rattings1 = findViewById<TextView>(R.id.Rattings)
        val name1 = findViewById<TextView>(R.id.Title)
        val price1 = findViewById<TextView>(R.id.Price)
        val description = findViewById<TextView>(R.id.Descrpition)
        val compile = findViewById<TextView>(R.id.compileid)
        val t1 = findViewById<TextView>(R.id.time)

        val price1_id = findViewById<TextView>(R.id.price_id_1)
        val price2_id = findViewById<TextView>(R.id.price_id_2)
        val price3_id = findViewById<TextView>(R.id.price_id_3)
        val price4_id = findViewById<TextView>(R.id.price_id_4)

        val calo = findViewById<TextView>(R.id.calori)
        val carb = findViewById<TextView>(R.id.carbo)
        val fat = findViewById<TextView>(R.id.fat)
        val fiber = findViewById<TextView>(R.id.fiber)
        val protein = findViewById<TextView>(R.id.protien)
        val sugar = findViewById<TextView>(R.id.sugar)

        myRecyclerView2 = findViewById(R.id.recycle1)

        val image = intent.getStringExtra("Image")
        val name = intent.getStringExtra("Name")
        val descriptionText = intent.getStringExtra("Description")
        val price = intent.getStringExtra("Price")
        val rattings = intent.getStringExtra("Ratting")
        val compilation = intent.getStringExtra("Compilation")

        val p1 = intent.getStringExtra("price1")
        val p2 = intent.getStringExtra("price2")
        val p3 = intent.getStringExtra("price3")
        val p4 = intent.getStringExtra("price4")

        val T1 = intent.getStringExtra("time")

        val cal = intent.getStringExtra("calori")
        val car = intent.getStringExtra("carbo")
        val fatValue = intent.getStringExtra("fat")
        val sug = intent.getStringExtra("sugar")
        val fib = intent.getStringExtra("fiber")
        val pro = intent.getStringExtra("protein")

        calo.text = "Calories: $cal"
        carb.text = "Carbohydrates: $car"
        fat.text = "Fat: $fatValue"
        sugar.text = "Sugar: $sug"
        fiber.text = "Fiber: $fib"
        protein.text = "Protein: $pro"

        rattings1.text = "$rattings people have positive ratings"
        name1.text = name
        price1.text = "₹$price"
        description.text = descriptionText
        Picasso.get().load(image).into(image1)

        price1_id.text = "Consumption Portion Price: ₹$p1"
        price2_id.text = "Consumption Total Price: ₹$p2"
        price3_id.text = "Portion Price: ₹$p3"
        price4_id.text = "Total Price: ₹$p4"

        t1.text = "Total time to ready: $T1 minutes"

        compile.setOnClickListener {
            val intent2 = Intent(this, compiler_details::class.java)
            intent2.putExtra("compile", compilation)
            startActivity(intent2)
        }

        val btnBuyNow = findViewById<Button>(R.id.btnBuyNow)
        val heartIcon = findViewById<ImageView>(R.id.heartIcon)

        btnBuyNow.setOnClickListener {
            val intent1 = Intent(this, order_page::class.java)
            Toast.makeText(this, "Processing...!", Toast.LENGTH_SHORT).show()
            intent1.putExtra("image", image)
            intent1.putExtra("name", name)
            startActivity(intent1)
        }

        var isLiked = false
        heartIcon.setOnClickListener {
            isLiked = if (!isLiked) {
                heartIcon.setImageResource(R.drawable.liked)

                // Add this product to the wishlist
                val wishlistItem = WishlistItem(image.toString(), name.toString(), price.toString())
                database = FirebaseDatabase.getInstance().getReference("users").child(userId).child("wishlist")

                val itemId = database.push().key
                itemId?.let {
                    database.child(it).setValue(wishlistItem).addOnSuccessListener {
                        Toast.makeText(this, "Added to the wishlist...!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Failed to add to the wishlist...!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                true
            } else {
                heartIcon.setImageResource(R.drawable.like)
                false
            }
        }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tasty.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)
        val service = retrofitBuilder.getdata(11, 20)
        service.enqueue(object : Callback<Apidata?> {
            override fun onResponse(call: Call<Apidata?>, response: Response<Apidata?>) {
                val data2 = response.body()?.results!!
                val myAdapter2 = MyADapter2(this@ProductDetailsActivity, data2)
                myRecyclerView2.adapter = myAdapter2
                myRecyclerView2.layoutManager = LinearLayoutManager(
                    this@ProductDetailsActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                Log.d("tag", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<Apidata?>, t: Throwable) {
                Log.d("tag", "onFailure: " + t.message)
            }
        })

        val topArrow = findViewById<ImageView>(R.id.toparrow)
        topArrow.setOnClickListener {
            finish()
        }
    }
}


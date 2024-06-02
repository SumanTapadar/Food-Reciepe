package com.example.nowweather

import android.annotation.SuppressLint

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class WishlistActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myAdapter: WishlistAdapter
    private val wishlistItems = mutableListOf<WishlistItem>()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.getStringExtra("userID2").toString()

        enableEdgeToEdge()
        setContentView(R.layout.activity_wishlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myRecyclerView = findViewById(R.id.wishlist_recycler_view)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter = WishlistAdapter(wishlistItems) { item -> removeItem(item) }
        myRecyclerView.adapter = myAdapter

        database = FirebaseDatabase.getInstance().getReference("users").child(userId).child("wishlist")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                wishlistItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val title = itemSnapshot.child("title").getValue(String::class.java)
                    val image = itemSnapshot.child("image").getValue(String::class.java)
                    val price = itemSnapshot.child("price").getValue(String::class.java)

                    Log.d("WishlistActivity", "Item retrieved: title=$title, image=$image, price=$price")  // Add this line

                    if (title != null && image != null && price != null) {
                        val wishlistItem = WishlistItem(image, title, price)
                        wishlistItems.add(wishlistItem)
                    } else {
                        Log.w("WishlistActivity", "Ignoring invalid item: $itemSnapshot")
                    }
                }
                myAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@WishlistActivity,
                    "Failed to load wishlist: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("WishlistActivity", "Failed to load wishlist", error.toException())
            }
        })

        val wishtop = findViewById<ImageView>(R.id.whislist_top)
        wishtop.setOnClickListener {
            finish()
        }
    }

    private fun removeItem(item: WishlistItem) {
        val itemRef = database.orderByChild("title").equalTo(item.title)
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {
                    itemSnapshot.ref.removeValue().addOnSuccessListener {
                        wishlistItems.remove(item)
                        myAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            this@WishlistActivity,
                            "Item removed from wishlist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@WishlistActivity,
                            "Failed to remove item: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WishlistActivity", "Failed to remove item", error.toException())
            }
        })
    }
}




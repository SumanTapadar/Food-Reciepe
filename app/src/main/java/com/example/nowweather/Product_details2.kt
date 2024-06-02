package com.example.nowweather

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class Product_details2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val image1=findViewById<ShapeableImageView>(R.id.Image0)
        val rattings1=findViewById<TextView>(R.id.Rattings0)
        val Name1=findViewById<TextView>(R.id.Title0)
        val price1=findViewById<TextView>(R.id.Price0)
        val des=findViewById<TextView>(R.id.Descrpition0)
        val compile=findViewById<CardView>(R.id.compileid0)
        val t1=findViewById<TextView>(R.id.time0)



        val price1_id=findViewById<TextView>(R.id.price_id_10)
        val price2_id=findViewById<TextView>(R.id.price_id_20)
        val price3_id=findViewById<TextView>(R.id.price_id_30)
        val price4_id=findViewById<TextView>(R.id.price_id_40)


        val Calo0=findViewById<TextView>(R.id.calori0)
        val Carb0=findViewById<TextView>(R.id.carbo0)
        val Fat0=findViewById<TextView>(R.id.fat0)
        val Fiber0=findViewById<TextView>(R.id.fiber0)
        val Protien0=findViewById<TextView>(R.id.protien0)
        val Sugar0=findViewById<TextView>(R.id.sugar0)


        val image0=intent.getStringExtra("Image0")
        val name0=intent.getStringExtra("Name0")
        val descri0=intent.getStringExtra("Description0")
        val price0=intent.getStringExtra("Price0")
        val rattings0=intent.getStringExtra("Ratting0")
        val comp0=intent.getStringExtra("compilation0")

        //price details
        val p10=intent.getStringExtra("price10")
        val p20=intent.getStringExtra("price20")
        val p30=intent.getStringExtra("price30")
        val p40=intent.getStringExtra("price40")

        //time tier
        val T10=intent.getStringExtra("time0")

        val cal0=intent.getStringExtra("calori0")
        val car0=intent.getStringExtra("carbo0")
        val fat0=intent.getStringExtra("fat0")
        val sug0=intent.getStringExtra("sugar0")
        val fib0=intent.getStringExtra("fiber0")
        val pro0=intent.getStringExtra("protien0")



        Calo0.text="Calories: "+cal0
        Carb0.text="Carbohydrates: "+car0
        Fat0.text="Fat: "+fat0
        Sugar0.text="Sugar: "+sug0
        Fiber0.text="Fiber: "+fib0
        Protien0.text="Protien: "+pro0

        rattings1.text=rattings0.toString()+"people have positive rattings"
        Name1.text=name0.toString()
        price1.text="₹"+price0.toString()
        des.text=descri0.toString()
        Picasso.get().load(image0).into(image1)

        price1_id.text="Consumption Portion Price : ₹"+p10.toString()
        price2_id.text="Comsumption Total Price : ₹"+p20.toString()
        price3_id.text="Portion Price : ₹"+p30.toString()
        price4_id.text="Total Price : ₹"+p40.toString()

        t1.text="Total time to ready : "+T10.toString()+" minutes"


        compile.setOnClickListener {
            val intent2= Intent(this,compiler_details::class.java)
            intent2.putExtra("compile",comp0.toString())
            startActivity(intent2)
        }

//        val BtnBuyNow=findViewById<Button>(R.id.btnBuyNow0)
        val HeartIcon=findViewById<ImageView>(R.id.heartIcon0)


//
//        BtnBuyNow.setOnClickListener {
////            val intetn1=Intent(this,order_page::class.java)
//            Toast.makeText(this,"Processing...!", Toast.LENGTH_SHORT).show()
////            intetn1.putExtra("image",image0)
////            intetn1.putExtra("name",name0)
////            startActivity(intetn1)
//        }



        var isliked=false
        HeartIcon.setOnClickListener {
            isliked = if(!isliked){
                HeartIcon.setImageResource(R.drawable.liked)
                Toast.makeText(this,"add to the wishlist...!", Toast.LENGTH_SHORT).show()
                true
            }else{
                HeartIcon.setImageResource(R.drawable.like)
                false
            }
        }


        val Toparrow=findViewById<ImageView>(R.id.toparrow0)
        Toparrow.setOnClickListener {
            finish()
        }

    }
}
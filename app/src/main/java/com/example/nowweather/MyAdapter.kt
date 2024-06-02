package com.example.nowweather

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MyAdapter(val context:Activity,val datalist:List<Result>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(context).inflate(R.layout.each_row,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val currentitem=datalist[position]
        holder.heading.text=currentitem.name
        holder.price.text="₹"+currentitem.price.total.toString()
        Picasso.get().load(currentitem.thumbnail_url).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent=Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("Name",currentitem.name)
            intent.putExtra("Image",currentitem.thumbnail_url)
            intent.putExtra("Price",currentitem.price.total.toString())
            intent.putExtra("Description",currentitem.description)
            intent.putExtra("Ratting",currentitem.user_ratings.count_positive.toString())

            //nuitritions
            intent.putExtra("calori",currentitem.nutrition.calories.toString())
            intent.putExtra("carbo",currentitem.nutrition.carbohydrates.toString())
            intent.putExtra("fat",currentitem.nutrition.fat.toString())
            intent.putExtra("protien",currentitem.nutrition.protein.toString())
            intent.putExtra("fiber",currentitem.nutrition.fiber.toString())
            intent.putExtra("sugar",currentitem.nutrition.sugar.toString())

///compilation
            intent.putExtra("compilation",currentitem.compilations.toString())
///price details
            intent.putExtra("price1",currentitem.price.consumption_portion.toString())
            intent.putExtra("price2",currentitem.price.consumption_total.toString())
            intent.putExtra("price3",currentitem.price.portion)
            intent.putExtra("price4",currentitem.price.total)

///time tier
            intent.putExtra("time",currentitem.total_time_tier?.display_tier)



            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image: ShapeableImageView
        val price: TextView
        val heading: TextView

        init {
            image = itemview.findViewById(R.id.profileimage)
            price = itemview.findViewById(R.id.price)
            heading = itemview.findViewById(R.id.heading)

        }
    }
}
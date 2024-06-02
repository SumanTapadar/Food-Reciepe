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


class MyADapter2(val context:Activity, val datalist2:List<Result>):
RecyclerView.Adapter<MyADapter2.MyViewHolder2>()

{
    class MyViewHolder2(itemview:View):RecyclerView.ViewHolder(itemview) {
val Image:ShapeableImageView
val Name:TextView

init {
    Image=itemview.findViewById(R.id.image1)
    Name=itemview.findViewById(R.id.name1)

}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyADapter2.MyViewHolder2 {
  val itemView=LayoutInflater.from(context).inflate(R.layout.each_card,parent,false)
        return MyViewHolder2(itemView)
    }

    override fun onBindViewHolder(holder: MyADapter2.MyViewHolder2, position: Int) {
        val currentitem=datalist2[position]
        holder.Name.text=currentitem.name

        Picasso.get().load(currentitem.thumbnail_url).into(holder.Image)
        holder.itemView.setOnClickListener {
            val intent=Intent(context,Product_details2::class.java)


            intent.putExtra("Name0",currentitem.name)
            intent.putExtra("Image0",currentitem.thumbnail_url)
            intent.putExtra("Price0",currentitem.price.total.toString())
            intent.putExtra("Description0",currentitem.description)
            intent.putExtra("Ratting0",currentitem.user_ratings.count_positive.toString())



            //nuitritions
            intent.putExtra("calori0",currentitem.nutrition.calories.toString())
            intent.putExtra("carbo0",currentitem.nutrition.carbohydrates.toString())
            intent.putExtra("fat0",currentitem.nutrition.fat.toString())
            intent.putExtra("protien0",currentitem.nutrition.protein.toString())
            intent.putExtra("fiber0",currentitem.nutrition.fiber.toString())
            intent.putExtra("sugar0",currentitem.nutrition.sugar.toString())




///price details
            intent.putExtra("price10",currentitem.price.consumption_portion.toString())
            intent.putExtra("price20",currentitem.price.consumption_total.toString())
            intent.putExtra("price30",currentitem.price.portion)
            intent.putExtra("price40",currentitem.price.total)

///time tier
            intent.putExtra("time0",currentitem.total_time_tier?.display_tier)


///compilation
            intent.putExtra("compilation0",currentitem?.compilations.toString())


            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
 return datalist2.size
    }
}
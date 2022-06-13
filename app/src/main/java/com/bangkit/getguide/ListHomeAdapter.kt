package com.bangkit.getguide

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.getguide.authentication.LoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListHomeAdapter (private val context:Context,private val listHome: ArrayList<ListHome>) : RecyclerView.Adapter<ListHomeAdapter.CardViewViewHolder>() {
    inner class CardViewViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var  imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var  tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var  imgLokasi: ImageView = itemView.findViewById(R.id.iv_item_lokasi)
        var  tvAlamat: TextView = itemView.findViewById(R.id.tv_item_alamat)
        var  tvRating1: TextView = itemView.findViewById(R.id.tv_item_rating1)
        var  imgRating2: ImageView = itemView.findViewById(R.id.iv_item_rating2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_home,parent,false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        val wisata = listHome[position]

        Glide.with(holder.itemView.context)
            .load(wisata.photo)
            .apply(RequestOptions())
            .into(holder.imgPhoto)

        holder.tvName.text = wisata.name
        holder.tvAlamat.text = wisata.alamat
        holder.tvRating1.text = wisata.rating1

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Kamu Memilih " + listHome[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)

        }

    }


    override fun getItemCount(): Int {
        return listHome.size
    }
}
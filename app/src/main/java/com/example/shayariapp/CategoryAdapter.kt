package com.example.shayariapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(var context: Context,var modelClassArray: ArrayList<Modelclass>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var  v = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)
        return  MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.txtName.setOnClickListener {
            var intent = Intent(context,ShowShayariActivity::class.java)
            intent.putExtra("id",modelClassArray.get(position).categoryId)
            context.startActivity(intent)
        }
        holder.txtName.setText(modelClassArray.get(position).categoryName)
    }

    override fun getItemCount(): Int {
        return modelClassArray.size

    }
}
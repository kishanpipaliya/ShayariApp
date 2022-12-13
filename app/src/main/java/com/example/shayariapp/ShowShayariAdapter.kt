package com.example.shayariapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ShowShayariAdapter(
    var context: Context, var modelClassArray: ArrayList<shayariModelClass>
) : RecyclerView.Adapter<ShowShayariAdapter.MyViewHolder>() {

    var myDatabase = MyDatabase(context)
    var i = 0
    var copyQuote = ""
    var color = intArrayOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7
    )
    var random = Random()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtQuote = view.findViewById<TextView>(R.id.txtQuote)
       // var cardQuote = view.findViewById<CardView>(R.id.cardQuote)
        var lnrQuoteShow = view.findViewById<LinearLayout>(R.id.lnrQuoteShow)
        var empty_heart = view.findViewById<ImageView>(R.id.empty_heart)
        var fill_heart = view.findViewById<ImageView>(R.id.fill_heart)
        var imgCopy = view.findViewById<ImageView>(R.id.imgCopy)
        var imgShare = view.findViewById<ImageView>(R.id.imgShare)
        var imgWhatsapp = view.findViewById<ImageView>(R.id.imgWhatsapp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.shayari_view, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        i = random.nextInt(color.size)




        holder.txtQuote.setText(modelClassArray.get(position).shayari)
        holder.lnrQuoteShow.setBackgroundResource(color.get(i))

        if(modelClassArray.get(position).status==1){
            holder.empty_heart.setVisibility(View.GONE)
            holder.fill_heart.setVisibility(View.VISIBLE)
        }

        holder.empty_heart.setOnClickListener {
            holder.empty_heart.setVisibility(View.GONE)
            holder.fill_heart.setVisibility(View.VISIBLE)
            Toast.makeText(context, "Add to Favorite", Toast.LENGTH_SHORT).show()
            myDatabase.likeUnlike(1,modelClassArray.get(position).shayariId)
        }
        holder.fill_heart.setOnClickListener {
            holder.fill_heart.setVisibility(View.GONE)
            holder.empty_heart.setVisibility(View.VISIBLE)
            myDatabase.likeUnlike(0,modelClassArray.get(position).shayariId)
            notifyDataSetChanged()
        }
        holder.imgCopy.setOnClickListener {
            copyQuote = holder.txtQuote.text.toString()
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text Copy", copyQuote)
            Toast.makeText(context, "Quotes is Copy", Toast.LENGTH_SHORT).show()
            clipboardManager.setPrimaryClip(clipData)
        }


        holder.imgShare.setOnClickListener {
            var shareQuotes = holder.txtQuote.text.toString()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareQuotes)
            Log.e(TAG, "onBindViewHolder: "+shareQuotes )
            context.startActivity(Intent.createChooser(intent, "share via"))
        }
        holder.imgWhatsapp.setOnClickListener {
            var shareWhatsapp = holder.txtQuote.text.toString()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, shareWhatsapp)
            context.startActivity(Intent.createChooser(intent, "share via"))
        }
        holder.txtQuote.setOnClickListener {
            var Quotes = holder.txtQuote.text.toString()
            var intent = Intent(context, FullScreenActivity::class.java)
            intent.putExtra("Quotes", Quotes)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return modelClassArray.size
    }

}
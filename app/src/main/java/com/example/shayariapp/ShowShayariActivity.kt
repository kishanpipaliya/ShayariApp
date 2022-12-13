package com.example.shayariapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ShowShayariActivity : AppCompatActivity() {
    lateinit var RecList: RecyclerView
    lateinit var catBackArrow: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_shayari)

        initView()

    }

    private fun initView() {

        RecList = findViewById(R.id.RecList)
        catBackArrow = findViewById(R.id.catBackArrow)
        var Id = intent.getIntExtra("id", 0)


        catBackArrow.setOnClickListener {
            finish()
        }

        var modelClassArray = MyDatabase(this).categoryClick(Id)

        var adapter = ShowShayariAdapter(this, modelClassArray)
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecList.layoutManager = manager
        RecList.adapter = adapter
    }
}
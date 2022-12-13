package com.example.shayariapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
//    lateinit var imgHome: ImageView
//    lateinit var imgCategory: ImageView
//    lateinit var imgMore: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
    }

    private fun initView() {
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
//        imgHome = findViewById(R.id.imgHome)
//        imgCategory = findViewById(R.id.imgCategory)
//        imgMore = findViewById(R.id.imgMore)

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home))
        tabLayout.addTab(tabLayout.newTab().setText("Category").setIcon(R.drawable.ic_category))
        tabLayout.addTab(tabLayout.newTab().setText("Favorite").setIcon(R.drawable.ic_empty_like))

//        selectPage(2)


        viewPager.post(object : Runnable {
            override fun run() {
                viewPager.currentItem = 1

            }
        })


        var tabAdapter = TabAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = tabAdapter
        tabview()

    }

    private fun tabview() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

}
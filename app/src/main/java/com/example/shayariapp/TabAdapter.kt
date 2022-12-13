package com.example.shayariapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(supportFragmentManager: FragmentManager, var tabCount: Int) :
    FragmentPagerAdapter(supportFragmentManager, tabCount) {
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {

         if (position == 0) {
            return HomeFragment()
        } else if (position == 1) {
            return CategoryFragment()
        } else if (position == 2) {
            return FavoriteFragment()
        }
        else{
            return CategoryFragment()
        }



    }


}
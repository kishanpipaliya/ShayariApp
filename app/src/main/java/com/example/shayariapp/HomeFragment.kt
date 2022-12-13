package com.example.shayariapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment() : Fragment() {
    lateinit var RcvList: RecyclerView
    lateinit var ModelList: ArrayList<shayariModelClass>
    lateinit var mydatabase: MyDatabase
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_home, container, false)

        initView()
        return v
    }

    private fun initView() {
        RcvList = v.findViewById(R.id.RcvList)


        mydatabase = MyDatabase(requireActivity())
        ModelList = mydatabase.AllCategory()


        var adapter = HomeAdapter(requireActivity(), ModelList)
        var manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        RcvList.layoutManager = manager
        RcvList.adapter = adapter

    }
    override fun onResume() {
        super.onResume()
        ModelList.clear()
        ModelList = mydatabase.AllCategory()


    }


}
package com.example.shayariapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CategoryFragment : Fragment() {

    lateinit var RcvList: RecyclerView
    lateinit var v: View
    lateinit var modelClassArray: ArrayList<Modelclass>
    lateinit var mydatabase: MyDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_category, container, false)
        initView()
        return v
    }

    private fun initView() {
        RcvList = v.findViewById(R.id.RcvList)

        mydatabase = MyDatabase(requireActivity())
        modelClassArray = mydatabase.readData()


        var adapter = CategoryAdapter(requireActivity(), modelClassArray)
        var manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        RcvList.layoutManager = manager
        RcvList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        modelClassArray.clear()
        modelClassArray = mydatabase.readData()


    }


}
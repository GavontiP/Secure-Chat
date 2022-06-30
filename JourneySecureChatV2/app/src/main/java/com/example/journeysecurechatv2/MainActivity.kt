package com.example.journeysecurechatv2

import ChannelListAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.channellistview)


//        val button = findViewById<Button>(R.id.button5)
        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
// getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_channel)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This loop will create 20 Views containing
        // the image with the count of view

            data.add(ItemsViewModel("Channel 1"))

        // This will pass the ArrayList to our Adapter
        val adapter1 = ChannelListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter1

//        val tabLayout = findViewById<TabLayout>(R.id.TabLayout)
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                startActivity(Intent(applicationContext, MessageActivity::class.java))
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // Handle tab reselect
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                startActivity(Intent(applicationContext, MainActivity::class.java))
//            }
//        })

    }

}


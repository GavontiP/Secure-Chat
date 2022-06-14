package com.example.journeysecurechatv2

import MessageListAdapter
import OtherMessageAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlin.concurrent.thread


class MessageActivity : AppCompatActivity(), MessageListener {

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    private val idVar: String = "id:" + getRandomString(8)
    // ArrayList of class ItemsViewModel
    private val data = ArrayList<ItemsViewModel>()
    private val data1 = ArrayList<OtherItemsModel>()
    private val serverUrl = "ws://10.0.2.2:8080"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_list_view)
        val button = findViewById<Button>(R.id.button_gchat_send)
        val userInput = findViewById<EditText>(R.id.edit_gchat_message)
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_gchat)
        WebSocketManager.init(serverUrl, this)
        // connect to websockets server as soon as chat  activity is started.
        thread {
            kotlin.run {
                WebSocketManager.connect()
            }
        }

        button.setOnClickListener {

            val message = object {
                val text = userInput.text
                val id: String = idVar
                // object expressions extend Any, so `override` is required on `toString()`
                override fun toString() = "$text $id"
            }

            if ( WebSocketManager .sendMessage( message.toString())) {
                addText( " Send from the client \n ")
            }

            runOnUiThread{
                // this creates a vertical layout Manager
                recyclerview.layoutManager = LinearLayoutManager(this)

                // your code to perform when the user clicks on the button
                data.add(ItemsViewModel(userInput.text.toString()))

                // This will pass the ArrayList to our Adapter
                val adapter = MessageListAdapter(data)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
                userInput.text.clear()
            }
        }

        val tabLayout = findViewById<TabLayout>(R.id.TabLayout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                startActivity(Intent(applicationContext, MessageActivity::class.java))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //WebSocketManager.close()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        })
        }

        override fun onConnectSuccess() {
            addText( " Connected successfully \n " )
        }

        override fun onConnectFailed() {
            addText( " Connection failed \n " )
        }

        override fun onClose() {
            addText( " Closed successfully \n " )
        }

        override fun onMessage(text: String?) {
            addText( " Receive message: $text \n " )
            postMessage(text);
        }

        private fun addText(text: String?) {
            runOnUiThread {
                val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }

    private fun postMessage(text: String?){
        println("id of this client:" + idVar.split(":")[1]);
        println("id of the sender:" + text.toString().split(":")[1]);
        if(text.toString().split(":")[1] == idVar.split(":")[1]){
            println("these are equal");

        }
        else{
            runOnUiThread {
                println("id of client receiving:" + idVar.split(":")[1]);
                //creates recycler view for other message
                val otherrecyclerview = findViewById<RecyclerView>(R.id.recycler_channelOther)
                // this creates a vertical layout Manager
                otherrecyclerview.layoutManager = LinearLayoutManager(this)
                data1.add(OtherItemsModel(text.toString().split(" ")[0]!!))

                // This will pass the ArrayList to our Adapter
                val adapter = OtherMessageAdapter(data1)

                // Setting the Adapter with the recyclerview
                otherrecyclerview.adapter = adapter
            }

        }
    }
        override fun onDestroy() {
            super .onDestroy ()
            WebSocketManager.close()
        }



    }



package com.example.journeysecurechatv2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


class MessageActivity : AppCompatActivity(), MessageListener {

    companion object{
        var userId = ""
    }

    private fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }
    private val idVar: String = "id:" + getRandomString()
    // ArrayList of class ItemsViewModel
    private val data = ArrayList<ItemsViewModel>()
    private val data1 = ArrayList<OtherItemsModel>()
    private val serverUrl = "ws://dry-temple-70159.herokuapp.com"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_list_view)
//        handleIntent(intent)

        val button = findViewById<Button>(R.id.button_gchat_send)
        val userInput = findViewById<EditText>(R.id.edit_gchat_message)
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_gchat)
        WebSocketManager.init(serverUrl, this)
        // connect to websockets server as soon as chat  activity is started.
        // toolbar
        val toolbar: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // add back arrow to toolbar

        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }


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
            postMessage(text)
        }
        private fun addText(text: String?) {
            runOnUiThread {
                val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun postMessage(text: String?){
        println("id of this client:" + idVar.split(":")[1])
        println("id of the sender:" + text.toString().split(":")[1])
        userId = text.toString().split(":")[1]
        if(text.toString().split(":")[1] == idVar.split(":")[1]){

        }
        else{
            runOnUiThread {
                println("id of client receiving:" + idVar.split(":")[1])
                //creates recycler view for other message
                val otherrecyclerview = findViewById<RecyclerView>(R.id.recycler_channelOther)
                // this creates a vertical layout Manager
                otherrecyclerview.layoutManager = LinearLayoutManager(this)
                data1.add(OtherItemsModel(text.toString()))

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



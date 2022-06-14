package com.example.journeysecurechatv2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MessageTest : AppCompatActivity(), MessageListener {
    private val serverUrl = "ws://10.0.2.2:8080"
    override fun onCreate(savedInstanceState: Bundle?) {
        super .onCreate (savedInstanceState)
        setContentView(R.layout.sockets_test)
        WebSocketManager.init(serverUrl, this)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val clientSendBtn = findViewById<Button>(R.id.clientSendBtn)
        val closeConnectionBtn = findViewById<Button>(R.id.closeConnectionBtn)
        connectBtn.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }
        clientSendBtn.setOnClickListener {
            if ( WebSocketManager .sendMessage( " Client send " )) {
                addText( " Send from the client \n " )
            }
        }
        closeConnectionBtn.setOnClickListener {
            WebSocketManager.close()
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
    }

    private fun addText(text: String?) {
        val contentEt = findViewById<EditText>(R.id.contentEt)
        runOnUiThread {
            contentEt.text.append(text)
        }
    }

    override fun onDestroy() {
        super .onDestroy ()
        WebSocketManager.close()
    }
}

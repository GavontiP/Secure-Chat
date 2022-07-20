package com.example.journeysecurechatv2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journeysecurechatv2.MainActivity.Companion.token
import com.example.journeysecurechatv2.MyFirebaseMessagingService.Companion.allowed
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


class ChannelListAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_list_item_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel.text
        holder.textView.setOnClickListener(channelOnClickListener)

    }


    private val channelOnClickListener =
        View.OnClickListener { view ->
if(!allowed){
    val thread = Thread {
        try {
            try{
                print("Token from the ChannelList Adapter: ")
                println(token)
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaTypeOrNull()
                val body =
                    "{\"delivery\":{\"method\":\"sms\",\"phoneNumber\":\"+17205858291\"},\"customer\":{\"uniqueId\":\"gavonti-test\"},\"callbackUrls\":[\"https://dry-temple-70159.herokuapp.com/callback?token=${token}\"],\"language\":\"en-US\",\"pipelineKey\":\"bd6cb621-eba1-4b2e-84e4-7e519daabf40\"}".toRequestBody(
                        mediaType
                    )
                print(body)
                val request = Request.Builder()
                    .url("https://app.journeyid.dev/api/system/executions")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2MiOiI2MmE3NTRiN2QzZGQ3NjA0YzJjMWE5MDUiLCJleHAiOjE2NjAzMTYzMDYsImlzcyI6ImpvdXJuZXkiLCJwcnAiOiJzeXN0ZW0ifQ.lPtlGv8O8tUoQMTanDk2Ws9aYjLT45Jf38B8bce3C-I")
                    .build()
                val response = client.newCall(request).execute()
                println(response)}
            finally {
                println("finally:.....")
                println(view.context)
                Timer().scheduleAtFixedRate( object : TimerTask() {
                    override fun run() {
                        if(allowed){
                            val context: Context = view.context
                            val intent = Intent(context, MessageActivity::class.java)
                            context.startActivity(intent)
                            cancel()

                        }
                        else{
                            println("Complete your pipeline to be allowed into the Secure Chat")
                        }
                    }
                }, 0, 1000)



            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    thread.start()
}
            else{
    val context: Context = view.context
    val intent = Intent(context, MessageActivity::class.java)
    context.startActivity(intent)
            }
        }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val textView1: TextView = itemView.findViewById(R.id.text_gchat_message_other)
        val textView: TextView = itemView.findViewById(R.id.button4)
    }

}


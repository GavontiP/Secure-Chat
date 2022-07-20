package com.example.journeysecurechatv2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journeysecurechatv2.MyUtils.Companion.currentDateTimeString

class OtherMessageAdapter(private val mList: ArrayList<OtherItemsModel>) : RecyclerView.Adapter<OtherMessageAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.other, parent, false)

        return ViewHolder(view)
    }
    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val otherItemsModel = mList[position]

        // sets the text to the textview from our itemHolder class
        if(otherItemsModel.text.split(";")[1] == "other"){
            holder.textView.text = otherItemsModel.text.split(";")[0];
            holder.textView1.text = currentDateTimeString
            holder.userName.text = "Other: " + MessageActivity.userId
        }
        else if (otherItemsModel.text.split(";")[1] == "me"){
            holder.textView.text = otherItemsModel.text.split(";")[0];
            holder.textView1.text = currentDateTimeString
            holder.userName.text = "Me"
            holder.textView.setBackgroundColor(Color.WHITE);
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val userName: TextView = itemView.findViewById(R.id.text_gchat_user_other)
        val textView1: TextView = itemView.findViewById(R.id.text_gchat_timestamp_other)
        val textView: TextView = itemView.findViewById(R.id.text_gchat_message_other)
    }
}


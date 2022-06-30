import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journeysecurechatv2.FirebaseUtils
import com.example.journeysecurechatv2.ItemsViewModel
import com.example.journeysecurechatv2.MessageActivity
import com.example.journeysecurechatv2.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class ChannelListAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_list_item_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text
        holder.textView.setOnClickListener(channelOnClickListener)

    }


    private val channelOnClickListener =
        View.OnClickListener { view ->
            val thread = Thread {
                try {
                    try{val client = OkHttpClient()
                        val mediaType = "application/json".toMediaTypeOrNull()
                        val body = RequestBody.create(mediaType, "{\"delivery\":{\"method\":\"sms\",\"phoneNumber\":\"+17205858291\"},\"customer\":{\"uniqueId\":\"gavonti-test\"},\"callbackUrls\":[\"https://us-central1-securechat-354721.cloudfunctions.net/helloWorld\"],\"language\":\"en-US\",\"pipelineKey\":\"bd6cb621-eba1-4b2e-84e4-7e519daabf40\"}")
                        val request = Request.Builder()
                            .url("https://app.journeyid.dev/api/system/executions")
                            .post(body)
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2MiOiI2MmE3NTRiN2QzZGQ3NjA0YzJjMWE5MDUiLCJleHAiOjE2NTkwMjYxMzksImlzcyI6ImpvdXJuZXkiLCJwcnAiOiJzeXN0ZW0ifQ.8HsokKX0emauIzVbhCNL4GsWJW1k5pzJfWBt3vDTMDI")
                            .build()
                        val response = client.newCall(request).execute()
                    println(response)}
                    finally {


                                FirebaseUtils().fireStoreDatabase.collection("users")
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        querySnapshot.forEach { document ->
                                            Log.d(TAG, "Read document with ID ${document.id}")
                                            Log.d(TAG, "Read data: ${document.data}")
                                            val parts = document.data.toString().split(" ")[1];
                                            println(parts);
                                            if(parts == "status=approved"){
                                                val context: Context = view.context
                                                val intent = Intent(context, MessageActivity::class.java)
                                                context.startActivity(intent)

                                        }
                                            else{

                                                println("Please complete authentication");
                                            }
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(TAG, "Error getting documents $exception")
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            thread.start()
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


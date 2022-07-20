package com.example.journeysecurechatv2

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen



class MainActivity : AppCompatActivity() {

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    companion object {
        var token = "h"
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            println("good to post notifications")
            // FCM SDK (and your app) can post notifications.
        } else {
            println("denied permissions to post notifications")
        }
    }

    // [START_EXCLUDE]
    @RequiresApi(33)
    // [END_EXCLUDE]
    private fun askNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            print("good to post notifications")
            // FCM SDK (and your app) can post notifications.
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            print("for app updates to post notifications")
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // Directly ask for the permission
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
    // [END ask_post_notifications]

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.channellistview)
        askNotificationPermission()

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_channel)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will create views based on data added

        data.add(ItemsViewModel("Channel 1"))

        // This will pass the ArrayList to our Adapter
        val adapter1 = ChannelListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter1

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
             token = task.result

            // Log and toast
            Log.d(TAG, token)
//            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

    }
}




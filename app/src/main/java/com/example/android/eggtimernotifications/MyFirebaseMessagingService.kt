package com.example.android.eggtimernotifications

import android.nfc.Tag
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i(TAG, "From: ${remoteMessage?.from}")
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {

    }
}
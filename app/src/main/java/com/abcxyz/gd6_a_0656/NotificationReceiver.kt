package com.abcxyz.gd6_a_0656

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("toastMessage")
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}
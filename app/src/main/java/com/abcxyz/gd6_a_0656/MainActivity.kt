package com.abcxyz.gd6_a_0656

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abcxyz.gd6_a_0656.databinding.ActivityMainBinding
import com.google.firebase.messaging.RemoteMessage

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        createNotificationChanel()

        binding!!.btn1.setOnClickListener{
            sendNotification()
        }

        binding!!.btn2.setOnClickListener{
            sendNotification()
        }
    }

    private fun createNotificationChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val desc = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = desc
            }

            val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = desc
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotification() {
        val intent: Intent = Intent(this,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val broadcastIntent : Intent = Intent(this,NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage",binding?.etMessage?.text.toString())

        val actionIntent = PendingIntent.getBroadcast(this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this,CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
            .setContentTitle(binding?.etTitle?.text.toString())
            .setContentText(binding?.etMessage?.text.toString())
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher,"Toast",actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId1,builder.build())
        }
    }

    private fun sendNotification2() {
        val builder = NotificationCompat.Builder(this,CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_two_24)
            .setContentTitle(binding?.etTitle?.text.toString())
            .setContentText(binding?.etMessage?.text.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId2,builder.build())
        }
    }
}
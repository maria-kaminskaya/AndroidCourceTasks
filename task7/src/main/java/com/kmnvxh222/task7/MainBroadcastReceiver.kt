package com.kmnvxh222.task7

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val intentService = Intent(context, MainIntentService::class.java)
        intentService.putExtra("action", action)
        context?.startService(intentService)
    }

}
package com.example.smsparserapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.smsparserapp.utils.parseSMS

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val messageBody = smsMessage.messageBody
                val parsedData = parseSMS(messageBody)
                parsedData?.let {
                    // Update UI with new data
                    // This could involve sending a broadcast to the activity or using a ViewModel
                }
            }
        }
    }
}
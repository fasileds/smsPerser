package com.example.smsparserapp.utils

import com.example.smsparserapp.models.SMSData
import java.text.SimpleDateFormat
import java.util.*

fun parseSMS(sms: String): SMSData? {
    val regex = """Date:\s*(\d{4}-\d{2}-\d{2}),\s*Time:\s*(\d{2}:\d{2}),\s*(CBE),\s*(Debit|Credit):\s*\$(\d+),\s*Balance:\s*\$(\d+)""".toRegex()
    val matchResult = regex.find(sms)

    return if (matchResult != null) {
        val (dateStr, time, bankName, transactionType, amount, balance) = matchResult.destructured
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)
        SMSData(
            date = date,
            bankName = bankName,
            time = time,
            transactionType = transactionType,
            amount = amount.toDouble(),
            balance = balance.toDouble()
        )
    } else {
        null
    }
}
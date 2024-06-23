package com.example.smsparserapp.models

import java.util.*

data class SMSData(
    val date: Date,
    val bankName: String,
    val time: String,
    val transactionType: String,
    val amount: Double,
    val balance: Double
)
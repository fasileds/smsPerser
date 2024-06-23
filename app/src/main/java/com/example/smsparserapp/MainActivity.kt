package com.example.smsparserapp

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smsparserapp.models.SMSData
import com.example.smsparserapp.utils.parseSMS
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val SMS_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            // Request SMS permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_SMS), SMS_PERMISSION_CODE)
        } else {
            // Permission already granted
            displaySMSData()
        }
    }

    // Handle the permission request response
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                displaySMSData()
            } else {
                // Permission denied
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displaySMSData() {
        // Retrieve SMS from sender with the specified address (e.g., CBE)
        val sms = getSmsFromSender("CBE")

        val parsedData = parseSMS(sms)

        parsedData?.let {
            findViewById<TextView>(R.id.dateTextView).text = "Date: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.date)}"
            findViewById<TextView>(R.id.bankNameTextView).text = "Bank Name: ${it.bankName}"
            findViewById<TextView>(R.id.timeTextView).text = "Time: ${it.time}"
            findViewById<TextView>(R.id.transactionTypeTextView).text = "Transaction Type: ${it.transactionType}"
            findViewById<TextView>(R.id.amountTextView).text = "Amount: ${it.amount}"
            findViewById<TextView>(R.id.balanceTextView).text = "Balance: ${it.balance}"
        }

        // Example data for chart
        val parsedSMSDataList = listOf(parsedData).filterNotNull()
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val entries = mutableListOf<Entry>()

        parsedSMSDataList.forEachIndexed { index, smsData ->
            entries.add(Entry(index.toFloat(), smsData.balance.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "Balance Over Time")
        val lineData = LineData(lineDataSet)
        lineChart.data = lineData

        val description = Description()
        description.text = "Balance Over Time"
        lineChart.description = description
        lineChart.invalidate() // refresh the chart
    }

    private fun getSmsFromSender(senderAddress: String): String {
        val uri = Uri.parse("content://sms/inbox")
        val cursor = contentResolver.query(uri, null, "address=?", arrayOf(senderAddress), "date DESC")

        var smsBody = ""
        cursor?.use { c ->
            if (c.moveToFirst()) {
                smsBody = c.getString(c.getColumnIndexOrThrow("body"))
            }
        }

        return smsBody
    }
}

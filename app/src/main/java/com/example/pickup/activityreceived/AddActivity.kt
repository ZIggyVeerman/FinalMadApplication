package com.example.pickup.activityreceived

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pickup.R
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class AddActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var packName: String
    private lateinit var owPostalCode: String
    private var owHomeNumber by Delegates.notNull<Int>()
    private lateinit var pickUpTime: Date
    private val format = SimpleDateFormat("dd MM HH mm", Locale.GERMAN)

    // view models
    private val viewModelMain: MainActivityViewModel by viewModels()
    private val viewModel: ReceivedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeViewModel()
        fab.setOnClickListener { savePackage() }
    }

    private fun observeViewModel() {
        viewModelMain.user?.observe(this, androidx.lifecycle.Observer { user ->
            this@AddActivity.user = user
        })
    }

    private fun savePackage() {
        if (checkFields()) {
            println(this.user.homeNumber)
            viewModel.addNewPackage(
                this.user.postalCode,
                this.user.homeNumber,
                packName,
                owPostalCode,
                owHomeNumber,
                pickUpTime
            )
            finish()
            startReceivedActivity()
        }
    }

    private fun checkFields(): Boolean {
        if (tifPackageName.text.isNullOrBlank()) {
            customToast(PACKAGENAME)
            return false
        }

        this.packName = tifPackageName.text.toString()

        if (tifPostalCode.text.isNullOrBlank()) {
            customToast(POSTALCODE)
            return false
        }
        this.owPostalCode = tifPostalCode.text.toString()

        if (tifHomeNumber.text.isNullOrBlank()) {
            customToast(HOMENUMBER)
            return false
        }
        this.owHomeNumber = tifHomeNumber.text.toString().toInt()

        if (tifMonth.text.isNullOrBlank()) {
            customToast(MONTH)
            return false
        }
        if (tifDay.text.isNullOrBlank()) {
            customToast(DAY)
            return false
        }
        if (tifHour.text.isNullOrBlank()) {
            customToast(HOUR)
            return false
        }
        if (tifMinute.text.isNullOrBlank()) {
            customToast(MINUTE)
            return false
        }
        val month = tifMonth.text.toString()
        val day = tifDay.text.toString()
        val hour = tifHour.text.toString()
        val minute = tifMinute.text.toString()

        try {
            val newDateTime: Date? = format.parse("$day $month $hour $minute")

            if (newDateTime != null) pickUpTime = newDateTime
        } catch (error: Error) {
            customToast("$ERROR$error")
            return false
        }

        return true
    }

    private fun customToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    private fun startReceivedActivity(){
        val intent = Intent(this, ReceivedActivity::class.java)
        startActivityForResult(intent, OK)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            startReceivedActivity()
            finish()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        const val PACKAGENAME = "Geef een titel"
        const val POSTALCODE = "Geef een platform"
        const val HOMENUMBER = "Geef een dag op"
        const val MONTH = "Geef een maand op"
        const val DAY = "Geef een dag op"
        const val HOUR = "Geef een uur op"
        const val MINUTE = "Geef een minuut op"
        const val ERROR = "Error zie fout: "
        const val OK = 100
    }
}

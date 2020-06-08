package com.example.pickup.activityreceived

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pickup.R
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class AddActivity : AppCompatActivity() {
    private lateinit var packName: String
    private lateinit var owPostalCode: String
    private var owHomeNumber by Delegates.notNull<Int>()
    private lateinit var pickUpTime: Date
    private val format = SimpleDateFormat("dd MM HH mm", Locale.GERMAN)

    private lateinit var viewModel: OwnerActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)

        fab.setOnClickListener{savePackage()}
    }

    private fun savePackage() {
        if (checkFields()) {
            //TODO remove  hardcoded string with good stuff
            viewModel.addNewPackage("8244DX", 36, packName, owPostalCode, owHomeNumber, pickUpTime)

            finish()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
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
    }
}

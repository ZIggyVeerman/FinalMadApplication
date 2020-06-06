package com.example.pickup.activityreceived

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.pickup.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class AddActivity : AppCompatActivity() {
    private lateinit var packName: String
    private lateinit var owPostalCode: String
    private var owHomeNumber by Delegates.notNull<Int>()
    private lateinit var pickUpTime: Date
    private val format = SimpleDateFormat("MM-dd HH:mm:ss", Locale.GERMAN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun savePackage(){
        if(checkFields()){

        }
    }
    private fun checkFields(): Boolean{
        return true
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
        const val PICKUPTIME = "Geef een maand op"
        const val YEAR = "Geef een geldig jaar op"
        const val ERROR = "Error zie fout: "
    }
}

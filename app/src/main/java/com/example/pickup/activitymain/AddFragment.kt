package com.example.pickup.activitymain

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class AddFragment : Fragment() {
    private lateinit var user: User
    private lateinit var packName: String
    private lateinit var owPostalCode: String
    private var owHomeNumber by Delegates.notNull<Int>()
    private lateinit var pickUpTime: Date

    //date stuff
    private val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val now: Calendar = Calendar.getInstance()

    //viewmodels
    private val viewModelMain: MainActivityViewModel by viewModels()
    private val viewModel: ReceivedActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the viewmodel
        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btshowDatePicker.setOnClickListener {
            val datePickerDialog = context?.let { context ->
                DatePickerDialog(
                    context, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        tvDate.text = "$dayOfMonth/$month/$year"
                    },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                )
            }
            datePickerDialog?.show()
        }

        btShowTimePicker.setOnClickListener {
            val timePickerDialog = context?.let { context ->
                TimePickerDialog(
                    context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        tvTime.text = "$hourOfDay:$minute"
                    },
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
                )
            }
            timePickerDialog?.show()
        }
        btConfirmNewPackage.setOnClickListener {
            savePackage()
        }
    }

    private fun observeViewModel() {
        viewModelMain.user?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            this.user = user
        })
    }

    private fun savePackage() {
        if (checkFields()) {
            viewModel.addNewPackage(
                this.user.postalCode,
                this.user.homeNumber,
                packName,
                owPostalCode,
                owHomeNumber,
                pickUpTime
            )
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

        if (tvDate.text.isNullOrBlank()) {
            customToast(Date)
            return false
        }
        if (tvTime.text.isNullOrBlank()) {
            customToast(Time)
            return false
        }

        val date: String = tvDate.text.toString()
        val time: String = tvTime.text.toString()

        try {
            val newDateTime: Date? = format.parse("$date $time")

            if (newDateTime != null) pickUpTime = newDateTime
        } catch (error: Error) {
            customToast("${ERROR}$error")
            return false
        }

        return true
    }

    private fun customToast(toast: String) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show()
    }


    private fun startReceivedActivity() {
        findNavController().navigate(R.id.action_AddFragment_to_ReceivedFragment)
    }

    companion object {
        const val PACKAGENAME = "Geef een titel"
        const val POSTALCODE = "Geef een platform"
        const val HOMENUMBER = "Geef een dag op"
        const val Date = "geef een datum"
        const val Time = "geef een tijd"
        const val ERROR = "Error zie fout: "
    }
}

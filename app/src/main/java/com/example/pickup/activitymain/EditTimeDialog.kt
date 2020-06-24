package com.example.pickup.activitymain

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.viewmodels.DialogViewModel
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.fragment_dialog.*
import java.text.SimpleDateFormat
import java.util.*

class EditTimeDialog : DialogFragment() {
    private val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val now: Calendar = Calendar.getInstance()
    private lateinit var ownerActivityViewModel: OwnerActivityViewModel
    private lateinit var dialogViewModel: DialogViewModel
    private lateinit var pickUpTime: Date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ownerActivityViewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
        dialogViewModel =
            ViewModelProvider(activity as AppCompatActivity).get(DialogViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_dialog, container, false)
        val btDate = rootView.findViewById<Button>(R.id.btDP)
        val btTime = rootView.findViewById<Button>(R.id.btTP)
        val btConfirm = rootView.findViewById<Button>(R.id.btConfirm)
        btConfirm.isEnabled = false

        btDate.setOnClickListener {
            val datePickerDialog = context?.let { context ->
                DatePickerDialog(
                    context, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        tvDate.text = "$dayOfMonth/$month/$year"
                        checkIfBoxesFull(btConfirm)
                    },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                )
            }
            datePickerDialog?.show()
        }

        btTime.setOnClickListener {
            val timePickerDialog = context?.let { context ->
                TimePickerDialog(
                    context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        tvTime.text = "$hourOfDay:$minute"
                        checkIfBoxesFull(btConfirm)
                    },
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
                )
            }
            timePickerDialog?.show()
        }

        btConfirm.setOnClickListener {
            println(dialogViewModel.ownerPostalCode)

            if (handleClick()) {
                ownerActivityViewModel.handleNo(
                    dialogViewModel.receivedPostalCode,
                    dialogViewModel.receivedHomeNumber,
                    dialogViewModel.ownerPostalCode,
                    dialogViewModel.ownerHomeNumber,
                    pickUpTime
                )
            }
            val action = EditTimeDialogDirections.actionEditTimeDialogFragmentToPackageFragment(100)
            findNavController().navigate(action)
        }
        return rootView
    }

    private fun checkIfBoxesFull(bt: Button) {
        if (!tvDate.text.isNullOrBlank()) {
            if (!tvTime.text.isNullOrBlank()) {
                bt.isEnabled = true
            }
        }
    }

    private fun handleClick(): Boolean {
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().navigate(R.id.action_editTimeDialogFragment_to_packageFragment)
    }

    companion object {
        const val Date = "geef een datum"
        const val Time = "geef een tijd"
        const val ERROR = "Error zie fout: "
    }
}

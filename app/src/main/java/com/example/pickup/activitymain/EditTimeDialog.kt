package com.example.pickup.activitymain

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import kotlinx.android.synthetic.main.fragment_dialog.*
import java.util.*


class EditTimeDialog : DialogFragment() {
    private val now: Calendar = Calendar.getInstance()


    fun newInstance(title: String?): EditTimeDialog? {
        val frag = EditTimeDialog()
        val args = Bundle()
        args.putString("title", title)
        frag.arguments = args
        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_dialog, container, false)
        val btDate = rootview.findViewById<Button>(R.id.btDP)
        val btTime = rootview.findViewById<Button>(R.id.btTP)
        val btConfirm = rootview.findViewById<Button>(R.id.btConfirm)

        btDate.setOnClickListener {
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

        btTime.setOnClickListener {
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

        btConfirm.setOnClickListener {
            val action = EditTimeDialogDirections.actionEditTimeDialogFragmentToPackageFragment(100)
            findNavController().navigate(action)
        }

        return rootview
        //TODO fix dit en package kiezen en dateparser dan app klaar
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().navigate(R.id.action_editTimeDialogFragment_to_packageFragment)
    }

}
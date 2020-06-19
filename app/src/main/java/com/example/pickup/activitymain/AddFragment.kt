package com.example.pickup.activitymain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.activityreceived.AddActivity
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.content_add.tifDay
import kotlinx.android.synthetic.main.content_add.tifHomeNumber
import kotlinx.android.synthetic.main.content_add.tifHour
import kotlinx.android.synthetic.main.content_add.tifMinute
import kotlinx.android.synthetic.main.content_add.tifMonth
import kotlinx.android.synthetic.main.content_add.tifPackageName
import kotlinx.android.synthetic.main.content_add.tifPostalCode
import kotlinx.android.synthetic.main.fragment_add.*
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var user: User
    private lateinit var packName: String
    private lateinit var owPostalCode: String
    private var owHomeNumber by Delegates.notNull<Int>()
    private lateinit var pickUpTime: Date
    private val format = SimpleDateFormat("dd MM HH mm", Locale.GERMAN)

    //viewmodels
    private val viewModelMain: MainActivityViewModel by viewModels()
    private val viewModel: ReceivedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the viewmodel
        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btTEST.setOnClickListener {
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
            println(this.user.homeNumber)
            viewModel.addNewPackage(
                this.user.postalCode,
                this.user.homeNumber,
                packName,
                owPostalCode,
                owHomeNumber,
                pickUpTime
            )
            startReceivedActivity()
            //TODO FINISH ZOEKEN
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

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

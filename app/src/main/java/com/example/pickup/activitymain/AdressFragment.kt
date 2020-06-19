package com.example.pickup.activitymain

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pickup.R
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.fragment_adress.*
import kotlin.properties.Delegates

class AdressFragment : Fragment() {
    private var pick: Int? = 0

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var ownerActivityViewModel: OwnerActivityViewModel
    private lateinit var postalCode: String
    private var homeNumber by Delegates.notNull<Int>()
    private val args: AdressFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize the Shared Activity ViewModel
        viewModel = ViewModelProvider(activity as AppCompatActivity)
            .get(MainActivityViewModel::class.java)

        ownerActivityViewModel = ViewModelProvider(activity as AppCompatActivity)
            .get(OwnerActivityViewModel::class.java)

        pick = args.myArg

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adress, container, false)
    }

    private fun saveGame() {
        if (checkFields()) {
            val user = User(postalCode, homeNumber)

            viewModel.insertUser(user)
            ownerActivityViewModel.addNewUser(postalCode, homeNumber)

            when (args.myArg) {
                //TODO TOAST
                0 -> println("iets ging fout")
                1 -> findNavController().navigate(R.id.action_AdressFragment_to_packageFragment)
                2 -> findNavController().navigate(R.id.action_AdressFragment_to_ReceivedFragment)
            }
        }
    }

    private fun checkFields(): Boolean {
        if (tifPostalCode.text.isNullOrBlank()) {
            customToast(POSTALCODE)
            return false
        }

        if (tifHomeNumber.text.isNullOrBlank()) {
            customToast(HOMENUMBER)
            return false
        }

        this.postalCode = tifPostalCode.text.toString()
        this.homeNumber = tifHomeNumber.text.toString().toInt()

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btSubmit).setOnClickListener {
            saveGame()
        }
    }

    private fun customToast(toast: String) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val POSTALCODE = "Geef een postecode"
        const val HOMENUMBER = "Geef een huisnummer"
    }
}

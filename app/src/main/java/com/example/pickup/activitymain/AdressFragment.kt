package com.example.pickup.activitymain

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pickup.R
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_adress.*
import kotlin.properties.Delegates

const val NEW_GAME = "NEW_GAME"

class AdressFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var postalCode: String
    private var homeNumber by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize the Shared Activity ViewModel
        viewModel = ViewModelProvider(activity as AppCompatActivity)
            .get(MainActivityViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adress, container, false)
    }

    private fun saveGame() {

        if (checkFields()) {
            val user = User(postalCode, homeNumber)

            viewModel.insertUser(user)
        }
    }

    private fun checkFields(): Boolean {
        if (tifPostalCode.text.isNullOrBlank()) {
            customToast(POSTALCODE)
            return false
        }

        this.postalCode = tifPostalCode.text.toString()

        if (tifHomeNumber.text.isNullOrBlank()) {
            customToast(HOMENUMBER)
            return false
        }
        this.homeNumber = tifHomeNumber.text.toString().toInt()

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btSubmit).setOnClickListener {
            saveGame()

//            val intent = Intent(activity, PackageActivity::class.java)
//            activity?.startActivity(intent)
        }
    }

    private fun customToast(toast: String) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val POSTALCODE = "Geef een postecode"
        const val HOMENUMBER = "Geef een huisnummer"
        const val ERROR = "Error zie fout: "
    }
}

package com.example.pickup.activitymain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.activityreceived.ReceivedActivity
import com.example.pickup.model.User
import com.example.pickup.repository.UserRepository
import com.example.pickup.viewmodels.MainActivityViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize the Shared Activity ViewModel
        viewModel = ViewModelProvider(activity as AppCompatActivity)
            .get(MainActivityViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun checkIfUser(): Boolean{
        if(viewModel.user != null){
            return true
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println(viewModel.iets)

        if (checkIfUser()) {
            view.findViewById<ImageView>(R.id.ivPackage).setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_AdressFragment)
            }
            view.findViewById<ImageView>(R.id.ivStorage).setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_AdressFragment)
            }
        } else {
            view.findViewById<ImageView>(R.id.ivPackage).setOnClickListener {
                println("TODO: GO TO PACKAGE OWNER SCREEN")
            }

            view.findViewById<ImageView>(R.id.ivStorage).setOnClickListener {
                println("TODO GO TO RECEIVED SCREEN")
//                val intent = Intent(activity, ReceivedActivity::class.java)
//                activity?.startActivity(intent)
            }
        }
    }
}

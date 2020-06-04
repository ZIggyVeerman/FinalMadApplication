package com.example.pickup.activitymain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.activityreceived.ReceivedActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.ivPackage).setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_AdressFragment)
        }

        view.findViewById<ImageView>(R.id.ivStorage).setOnClickListener{
            val intent = Intent(activity, ReceivedActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}

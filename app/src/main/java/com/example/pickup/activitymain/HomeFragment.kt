package com.example.pickup.activitymain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
import com.example.pickup.activitypackage.PackageActivity
import com.example.pickup.activityreceived.ReceivedActivity
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the viewModel
        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(activity as AppCompatActivity)
            .get(MainActivityViewModel::class.java)
        viewModel.user?.observe(viewLifecycleOwner, Observer { user ->
            this.user = user
        })

    }

    private fun checkIfUser(intent: Intent, where: Int) {
        if (this.user == null) {
            INTENT = intent
            findNavController().navigate(R.id.action_homeFragment_to_AdressFragment)
        } else {
            activity?.startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.ivPackage).setOnClickListener {
            val intent = Intent(activity, PackageActivity::class.java)
            checkIfUser(intent, 1)
        }

        view.findViewById<ImageView>(R.id.ivStorage).setOnClickListener {
            val intent = Intent(activity, ReceivedActivity::class.java)
            checkIfUser(intent, 2)
        }
    }

    companion object {
        var INTENT: Intent? = null
    }
}

package com.example.pickup.activitymain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pickup.activitypackage.PackageActivity
import com.example.pickup.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AdressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btSearch).setOnClickListener {
            val intent = Intent(activity, PackageActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}

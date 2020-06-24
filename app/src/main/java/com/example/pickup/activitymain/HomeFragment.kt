package com.example.pickup.activitymain

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pickup.R
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

        setHasOptionsMenu(true)

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

    private fun checkIfUser(pick: Int, navigate: Int) {
        if (this.user == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToAdressFragment(navigate)
            findNavController().navigate(action)
        } else {
            findNavController().navigate(pick)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.ivPackage).setOnClickListener {
            checkIfUser(R.id.action_HomeFragment_to_packageFragment, 1)
        }

        view.findViewById<ImageView>(R.id.ivStorage).setOnClickListener {
            checkIfUser(R.id.action_HomeFragment_to_ReceivedFragment, 2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun deleteUserData() {
        viewModel.deleteAllUsers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_user -> {
                deleteUserData()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

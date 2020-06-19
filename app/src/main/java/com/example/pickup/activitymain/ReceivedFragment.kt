package com.example.pickup.activitymain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.adapters.ReceivedPackageAdapter
import com.example.pickup.model.PackageItem
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.activity_received.*
import kotlinx.android.synthetic.main.content_received.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReceivedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceivedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val packages = arrayListOf<PackageItem>()
    private val receivedPackageAdapter = ReceivedPackageAdapter(packages) { packageItem ->
        onPackageClick(packageItem)
    }
    private lateinit var viewModelReceived: ReceivedActivityViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User

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
        // inflate viewmode
        observeViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received, container, false)
    }

    private fun initViews() {
        rvReceivedPackages.layoutManager = LinearLayoutManager(activity)
        rvReceivedPackages.adapter = receivedPackageAdapter
        createItemTouchHelper().attachToRecyclerView(rvReceivedPackages)

        viewModelReceived.getPackages(this.user.postalCode, this.user.homeNumber)

//        fab.setOnClickListener {
//            startAddActivity()
//            //finish()
//        }

    }

    private fun observeViewModel() {
        viewModelReceived = ViewModelProvider(this).get(ReceivedActivityViewModel::class.java)
        viewModelReceived.packages.observe(viewLifecycleOwner, Observer { packageItem ->
            packages.clear()
            packages.addAll(packageItem)
            receivedPackageAdapter.notifyDataSetChanged()
        })
        viewModelMain.user?.observe(viewLifecycleOwner, Observer { user ->
            this.user = user
            initViews()
        })

    }

    private fun onPackageClick(packageItem: PackageItem) {
        // accept new date when incomming
    }

    private fun startAddActivity() {
        findNavController().navigate(R.id.action_ReceivedFragment_to_AddFragment)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val packageToDelete = packages[position]

                viewModelReceived.deletePackageFromList(user, packageToDelete)
                observeViewModel()

                //TODO melding met dat het verwijderd is
            }
        }
        return ItemTouchHelper(callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReceivedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceivedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

package com.example.pickup.activitymain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pickup.R
import com.example.pickup.adapters.ReceivedPackageAdapter
import com.example.pickup.model.PackageItem
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.activity_received.fab
import kotlinx.android.synthetic.main.content_received.rvReceivedPackages
import kotlinx.android.synthetic.main.fragment_received.*

class ReceivedFragment : Fragment() {
    private val packages = arrayListOf<PackageItem>()
    private val receivedPackageAdapter = ReceivedPackageAdapter(packages) { packageItem ->
        onPackageClick(packageItem)
    }
    private lateinit var viewModelReceived: ReceivedActivityViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate viewmode
        observeViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeContainer: SwipeRefreshLayout = view.findViewById(R.id.swiperefresh)

        swipeContainer.setOnRefreshListener {
            observeViewModel()
            swiperefresh.isRefreshing = false
        }
    }

    private fun initViews() {
        rvReceivedPackages.layoutManager = LinearLayoutManager(activity)
        rvReceivedPackages.adapter = receivedPackageAdapter
        createItemTouchHelper().attachToRecyclerView(rvReceivedPackages)

        viewModelReceived.getPackages(this.user.postalCode, this.user.homeNumber)

        fab.setOnClickListener {
            startAddActivity()
        }
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

                Toast.makeText(
                    context,
                    "${packageToDelete.packageName} is uit lijst verwijderd",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        return ItemTouchHelper(callback)
    }
}

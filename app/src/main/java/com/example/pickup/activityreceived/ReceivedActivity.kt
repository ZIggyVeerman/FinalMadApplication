package com.example.pickup.activityreceived

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.activitymain.MainActivity
import com.example.pickup.adapters.ReceivedPackageAdapter
import com.example.pickup.model.PackageItem
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.activity_received.*
import kotlinx.android.synthetic.main.content_received.*

const val ADD_NEW_PACKAGE_CODE = 100

class ReceivedActivity : AppCompatActivity() {
    private val packages = arrayListOf<PackageItem>()
    private val receivedPackageAdapter = ReceivedPackageAdapter(packages) { packageItem ->
        onPackageClick(packageItem)
    }
    private lateinit var viewModelReceived: ReceivedActivityViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_received)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        observeViewModel()
    }

    private fun initViews() {
        rvReceivedPackages.layoutManager = LinearLayoutManager(this)
        rvReceivedPackages.adapter = receivedPackageAdapter
        createItemTouchHelper().attachToRecyclerView(rvReceivedPackages)

        viewModelReceived.getPackages(this.user.postalCode, this.user.homeNumber)

        fab.setOnClickListener {
            startAddActivity()
            finish()
        }

    }

    private fun observeViewModel() {
        viewModelReceived = ViewModelProvider(this).get(ReceivedActivityViewModel::class.java)
        viewModelReceived.packages.observe(this, Observer { packageItem ->
            packages.clear()
            packages.addAll(packageItem)
            receivedPackageAdapter.notifyDataSetChanged()
        })
        viewModelMain.user?.observe(this, Observer { user ->
            this@ReceivedActivity.user = user
            initViews()
        })

    }

    private fun onPackageClick(packageItem: PackageItem) {
        // accept new date when incomming
    }

    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, ADD_NEW_PACKAGE_CODE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

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

}
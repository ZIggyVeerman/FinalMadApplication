package com.example.pickup.activityreceived

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
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

        viewModelReceived.getPackages(this.user.postalCode, this.user.homeNumber)

        fab.setOnClickListener {
            startAddActivity()
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

    }

    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, ADD_NEW_PACKAGE_CODE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

}
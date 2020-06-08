package com.example.pickup.activitypackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
import com.example.pickup.activitymain.MainActivity
import com.example.pickup.adapters.OwnerPackageAdapter
import com.example.pickup.model.PackageOverviewResponse
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_package.*

class PackageActivity : AppCompatActivity() {
    private val overview = arrayListOf<PackageOverviewResponse>()
    private val ownerPackageAdapter = OwnerPackageAdapter(overview)
    private lateinit var viewModel: OwnerActivityViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        observeViewModel()
    }

    private fun initViews() {
        rvOwnerPackages.layoutManager = LinearLayoutManager(this)
        rvOwnerPackages.adapter = ownerPackageAdapter
        viewModel.getPackageForOwner(this.user.postalCode, this.user.homeNumber)

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
        viewModel.overview.observe(this, Observer { list ->
            overview.clear()
            overview.addAll(list)
            ownerPackageAdapter.notifyDataSetChanged()
        })
        viewModelMain.user?.observe(this, Observer { user ->
            this@PackageActivity.user = user
            initViews()
        })
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

}
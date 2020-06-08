package com.example.pickup.activitypackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
import com.example.pickup.adapters.OwnerPackageAdapter
import com.example.pickup.model.UserWithPackageItem
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.content_package.*

class PackageActivity : AppCompatActivity() {
    private val packages = arrayListOf<UserWithPackageItem>()
    private val ownerPackageAdapter = OwnerPackageAdapter(packages)
    private lateinit var viewModel: OwnerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        setSupportActionBar(null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

//        observeViewModel()
        initViews()
    }

    private fun initViews() {
        println(this)
        rvOwnerPackages.layoutManager = LinearLayoutManager(this)
        rvOwnerPackages.adapter = ownerPackageAdapter
        viewModel.getPackageForOwner("8244DJ", 36)

    }

//    private fun observeViewModel() {
//        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
//        viewModel.packages.observe(this, Observer {
//            packages.clear()
//            packages.addAll(it)
//            ownerPackageAdapter.notifyDataSetChanged()
//        })
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

}
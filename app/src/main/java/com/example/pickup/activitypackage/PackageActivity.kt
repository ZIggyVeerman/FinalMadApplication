package com.example.pickup.activitypackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
import com.example.pickup.adapters.OwnerPackageAdapter
import com.example.pickup.model.PackageItem
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_package.*

class PackageActivity : AppCompatActivity() {
    private val packages = arrayListOf<PackageItem>()
    private val ownerPackageAdapter = OwnerPackageAdapter(packages)
    private lateinit var viewModel: OwnerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        setSupportActionBar(null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        observeViewModel()
        initViews()
    }

    private fun initViews() {
        rvOwnerPackages.layoutManager = LinearLayoutManager(this)
        rvOwnerPackages.adapter = ownerPackageAdapter
//        viewModel.getPackages("8244DX", 36)

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
        viewModel.packages.observe(this, Observer { packageItem ->
            packages.clear()
            packages.addAll(packageItem)
            ownerPackageAdapter.notifyDataSetChanged()
        })
    }

}
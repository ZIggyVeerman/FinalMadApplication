package com.example.pickup.activitymain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
import com.example.pickup.adapters.OwnerPackageAdapter
import com.example.pickup.model.PackageOverviewResponse
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel
import com.example.pickup.viewmodels.OwnerActivityViewModel
import kotlinx.android.synthetic.main.fragment_package.*

class PackageFragment : Fragment() {
    private val overview = arrayListOf<PackageOverviewResponse>()
    private val ownerPackageAdapter =
        OwnerPackageAdapter(overview, { overViewItem: PackageOverviewResponse ->
            onYesClick(overViewItem)
        }, { overViewItem: PackageOverviewResponse -> onNoClick(overViewItem) }
        )
    private lateinit var viewModel: OwnerActivityViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate viewmodel
        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package, container, false)
    }

    private fun onYesClick(overviewItem: PackageOverviewResponse) {
        println(overviewItem.packages[0].packageName)
    }

    private fun onNoClick(overviewItem: PackageOverviewResponse) {
        println(overviewItem.packages[0].packageName)
    }

    private fun initViews() {
        rvOwnerPackages.layoutManager = LinearLayoutManager(activity)
        rvOwnerPackages.adapter = ownerPackageAdapter
        viewModel.getPackageForOwner(this.user.postalCode, this.user.homeNumber)

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
        viewModel.overview.observe(viewLifecycleOwner, Observer { list ->
            overview.clear()
            overview.addAll(list)
            ownerPackageAdapter.notifyDataSetChanged()
        })
        viewModelMain.user?.observe(viewLifecycleOwner, Observer { user ->
            this.user = user
            initViews()
        })
    }
}

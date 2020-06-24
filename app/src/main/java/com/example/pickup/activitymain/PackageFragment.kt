package com.example.pickup.activitymain

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickup.R
import com.example.pickup.adapters.OwnerPackageAdapter
import com.example.pickup.model.PackageOverviewResponse
import com.example.pickup.model.User
import com.example.pickup.viewmodels.DialogViewModel
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
    private lateinit var dialogViewModel: DialogViewModel
    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var user: User
    private val args: PackageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate viewmodel
        observeViewModel()
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (args.confirm == 100) {
            Toast.makeText(context, "New time has been send", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onYesClick(overviewItem: PackageOverviewResponse) {
        viewModel.handleYes(
            overviewItem.postalCode,
            overviewItem.homeNumber,
            overviewItem.packages[0].ownerPostalCode,
            overviewItem.packages[0].ownerHomeNumber
        )
    }

    private fun onNoClick(overviewItem: PackageOverviewResponse) {
        dialogViewModel.storeFields(
            overviewItem.packages[0].ownerPostalCode,
            overviewItem.packages[0].ownerHomeNumber,
            overviewItem.postalCode,
            overviewItem.homeNumber
        )
        findNavController().navigate(R.id.action_packageFragment_to_editTimeDialogFragment)
    }

    private fun initViews() {
        rvOwnerPackages.layoutManager = LinearLayoutManager(activity)
        rvOwnerPackages.adapter = ownerPackageAdapter
        viewModel.getPackageForOwner(this.user.postalCode, this.user.homeNumber)

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(OwnerActivityViewModel::class.java)
        dialogViewModel =
            ViewModelProvider(activity as AppCompatActivity).get(DialogViewModel::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_packageFragment_to_HomeFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

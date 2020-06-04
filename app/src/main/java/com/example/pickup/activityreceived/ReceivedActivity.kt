package com.example.pickup.activityreceived

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pickup.R
import com.example.pickup.adapters.ReceivedPackageAdapter
import com.example.pickup.model.PackageItem
import com.example.pickup.viewmodels.ReceivedActivityViewModel
import kotlinx.android.synthetic.main.content_received.*

const val PACKAGE_EXTRA = "EXTRA"

class ReceivedActivity : AppCompatActivity() {

    private val packages = arrayListOf<PackageItem>()
    private val receivedPackageAdapter = ReceivedPackageAdapter(packages) { packageItem ->
        onPackageClick(packageItem)
    }
    private lateinit var viewModel: ReceivedActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_received)

        initViews()
        observeViewModel()

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
////            val fragment = InfoFragment::class.java
////            FragmentTransaction transaction
//        }
    }

    private fun initViews() {
        rvReceivedPackages.adapter = receivedPackageAdapter
        //TODO STATIC FOR TESTING moet weg
        viewModel.getPackages("8244DX", 36)


    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(ReceivedActivityViewModel::class.java)
        viewModel.packages.observe(this, Observer { packageItem ->
            packages.clear()
            packages.addAll(packageItem)
            receivedPackageAdapter.notifyDataSetChanged()
        })
    }

    private fun onPackageClick(packageItem: PackageItem) {
        println(packageItem)

//        val intent = Intent(this, Detail::class.java)
//        intent.putExtra(MOVIE_EXTRA, movieItem)
//        startActivity(intent)
    }
}
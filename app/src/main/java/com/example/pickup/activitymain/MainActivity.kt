package com.example.pickup.activitymain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.pickup.R
import com.example.pickup.viewmodels.DialogViewModel
import com.example.pickup.viewmodels.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var dialogViewModel: DialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        dialogViewModel = ViewModelProvider(this).get(DialogViewModel::class.java)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }
}

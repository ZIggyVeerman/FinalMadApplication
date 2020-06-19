package com.example.pickup.activitymain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.pickup.R
import com.example.pickup.model.User
import com.example.pickup.viewmodels.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import java.util.EnumSet.of

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initNavigation()
    }

    private fun deleteUserData() {
        mainActivityViewModel.deleteAllUsers()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val navController = findNavController(R.id.nav_host_fragment)
        return when (item.itemId) {
            R.id.action_delete_user -> {
                deleteUserData()
                return true
            }
            android.R.id.home -> {
                navController.navigateUp()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

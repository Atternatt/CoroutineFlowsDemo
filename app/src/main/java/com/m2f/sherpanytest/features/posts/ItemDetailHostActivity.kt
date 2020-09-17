package com.m2f.sherpanytest.features.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.m2f.sherpanytest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailHostActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_item_detail)

    val navController = findNavController(R.id.nav_host_fragment_item_detail)
    appBarConfiguration = AppBarConfiguration(navController.graph)
    setupActionBarWithNavController(navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_item_detail)
    return navController.navigateUp(appBarConfiguration)
        || super.onSupportNavigateUp()
  }
}
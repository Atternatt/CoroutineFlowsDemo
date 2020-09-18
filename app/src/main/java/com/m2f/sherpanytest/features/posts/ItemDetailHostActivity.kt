package com.m2f.sherpanytest.features.posts

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.databinding.ActivityItemDetailBinding
import com.m2f.sherpanytest.di.viewmodel.ViewModelFactory
import com.m2f.sherpanytest.utils.findChildrenByClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailHostActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    //as the scope of this ViewModel is ActivityRetainedScope it can be shared for all the elements of the activity scope
    //including the fragments.
    private val vm: PostsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostsViewModel::class.java)
    }

    val binding by lazy { ActivityItemDetailBinding.inflate(layoutInflater) }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.viewModel = vm

        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.findChildrenByClass(TextView::class.java).forEach {
            it.setTextColor(Color.WHITE)
        }


        //fixme @Marc -> Current Limitation as we inflate the searchview with a menu How can we bind it with databinding?
        /**This listener will call for [PostsViewModel.updatefilter] manually instead of using a two-way binding*/
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                vm.updatefilter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                vm.updatefilter(newText)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
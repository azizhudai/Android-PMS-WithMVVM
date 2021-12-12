package com.mindfulness.android_pms.ui.main

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.navigation.ui.AppBarConfiguration
import com.mindfulness.android_pms.R
import androidx.appcompat.widget.Toolbar
import com.mindfulness.android_pms.utils.startProjectAddActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    //
   /* override val kodein by kodein() KodeinAware
    private val factory : HeaderViewModelFactory by instance()
    private lateinit var viewModel: HeaderViewModel*/
    //
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab_main)
        fab.setOnClickListener { view ->
          /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
*/
            startProjectAddActivity()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout_main)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val mHeaderView = navView.getHeaderView(0)

        val mDrawerHeaderTitle = mHeaderView.findViewById(R.id.tvNav_header_title) as TextView
        val mDrawerHeaderSubTitle = mHeaderView.findViewById(R.id.tvNav_header_subtitle) as TextView
        mDrawerHeaderTitle.text = firebaseAuth.currentUser?.displayName
        mDrawerHeaderSubTitle.text = firebaseAuth.currentUser?.email

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_project,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //val tvNav_header = findViewById<TextView>(R.id.tvNav_header_subtitle)
       // tvNav_header.setText("private")
       /* val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(HeaderViewModel::class.java)
        binding.viewmodel2 = viewModel
*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

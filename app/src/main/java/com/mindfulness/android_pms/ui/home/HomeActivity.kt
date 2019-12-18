package com.mindfulness.android_pms.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.databinding.ActivityHomeBinding

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel

    }

}

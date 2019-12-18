package com.mindfulness.android_pms.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindfulness.android_pms.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }

}
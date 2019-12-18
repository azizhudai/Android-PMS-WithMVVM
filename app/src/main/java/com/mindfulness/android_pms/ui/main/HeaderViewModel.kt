package com.mindfulness.android_pms.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import com.mindfulness.android_pms.data.repositories.UserRepository
import com.mindfulness.android_pms.utils.startLoginActivity


class HeaderViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }
    
    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }
}
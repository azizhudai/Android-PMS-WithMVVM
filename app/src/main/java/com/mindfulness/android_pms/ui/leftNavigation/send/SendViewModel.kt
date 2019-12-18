package com.mindfulness.android_pms.ui.leftNavigation.send


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindfulness.android_pms.data.repositories.UserRepository

class SendViewModel (
    private val repository: UserRepository
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is send Fragment"
    }
    val text: LiveData<String> = _text

    fun logout(){
        repository.logout()
        //Toast.makeText(this, user.displayName.toString(), Toast.LENGTH_LONG).show()
        //view.context.startLoginActivity()
    }
}
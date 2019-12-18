package com.mindfulness.android_pms.ui.auth

import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.firebase.FirebaseSource
import com.mindfulness.android_pms.data.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class AuthViewModel(
    private val repository: UserRepository

) : ViewModel() {

    //email and password for the input
    var email: String? = null
    var password: String? = null

    //auth listener
    var authListener: AuthListener? = null


    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    //function to perform login
    fun login() {

        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                try {
                    authListener?.onSuccess()
                }catch (e:Exception) {
                    authListener?.onFailure("Başarısız!")
                }

            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    //Doing same thing with signup
    fun signup() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        else if(password!!.length <6){
            authListener?.onFailure("password must be least 6 character")
            return
        }
        authListener?.onStarted()
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToGoogleApi(view:View) {

    }

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
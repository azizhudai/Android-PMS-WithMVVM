package com.mindfulness.android_pms.data.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.mindfulness.android_pms.data.firebase.FirebaseSource

class UserRepository (
    private val firebase: FirebaseSource
){
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) = firebase.firebaseAuthWithGoogle(acct)
}
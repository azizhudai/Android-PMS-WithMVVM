package com.mindfulness.android_pms.ui.auth.firebase

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mindfulness.android_pms.R

 class GoogleApi {

    // google sign in
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

   // configureGoogleSignIn()
   // setupUI()
}


package com.mindfulness.android_pms.ui.leftNavigation.send

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.ui.auth.LoginActivity
import com.mindfulness.android_pms.utils.startLoginActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_send, container, false)

        //val binding: SendFragment = DataBindingUtil.setContentView<Fragment>( activity,R.layout.fragment_send)
        //iewModel = ViewModelProviders.of(activity, factory).get(SendViewModel::class.java)
        //binding.viewModel = viewModel  (override val kodein: Kodein)

        /*val textView: TextView = root.findViewById(R.id.text_send)
        sendViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        //sendViewModel.logout(root.rootView)
/*
         val repository: UserRepository
        repository.logout()
        view.context.startLoginActivity()  (
            private val repository: UserRepository
        )*/
        /* val factory: SendViewModelFactory = SendViewModelFactory()

         lateinit var viewModel: SendViewModel
         val binding = ViewModelProviders.of(activity!!, factory).get(SendViewModel::class.java)
          binding.logout()*/

        firebaseAuth.signOut()

        Intent(activity, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

        return root
    }

    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)

          //val factory: SendViewModelFactory by instance()

          //lateinit var viewModel: SendViewModel
          //val binding = ViewModelProviders.of(activity!!, factory).get(SendViewModel::class.java)
         // binding.logout()
      }*/
}
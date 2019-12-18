package com.mindfulness.android_pms

import android.app.Application
import com.mindfulness.android_pms.data.firebase.FirebaseSource
import com.mindfulness.android_pms.data.repositories.ProjectRepository
import com.mindfulness.android_pms.data.repositories.UserRepository
import com.mindfulness.android_pms.ui.auth.AuthViewModelFactory
import com.mindfulness.android_pms.ui.home.HomeViewModelFactory
import com.mindfulness.android_pms.ui.leftNavigation.project.event.ProjectAddViewModelFactory
import com.mindfulness.android_pms.ui.leftNavigation.send.SendViewModel
import com.mindfulness.android_pms.ui.leftNavigation.send.SendViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class FirebaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepository(instance()) }
        bind() from singleton { ProjectRepository(instance()) }
        bind() from provider { ProjectAddViewModelFactory(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
      /* val aa = bind() from provider {
           SendViewModelFactory(
               instance()
           )
       }*/
    }

}
package com.mindfulness.android_pms.ui.leftNavigation.project.event

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.databinding.ActivityProjectAddBinding
import com.mindfulness.android_pms.ui.auth.AuthListener
import com.mindfulness.android_pms.utils.startMainMenuActivity
import kotlinx.android.synthetic.main.activity_project_add.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS", "UNUSED_VALUE")
class ProjectAddActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: ProjectAddViewModelFactory by instance()
    private lateinit var viewModel: ProjectAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_project_add)


        val binding: ActivityProjectAddBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_project_add)
        viewModel = ViewModelProviders.of(this, factory).get(ProjectAddViewModel::class.java)
        binding.viewmodel = viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.authListener = this

        viewModel.startDateLiveData.observe(this, Observer {
            tv_ProjectStartDate.text = it
        })
        viewModel.endDateLiveData.observe(this, Observer {
            tv_ProjectEndDate.text = it
        })

        // live check between dates
        viewModel.startStatusLiveData.observe(this, Observer {
            Toast.makeText(
                this@ProjectAddActivity,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
        viewModel.endStatusLiveData.observe(this, Observer {
            Toast.makeText(
                this@ProjectAddActivity,
                it,
                Toast.LENGTH_LONG
            ).show()
        })

}

fun projectStartDateClick(view: View) {

 DatePickerDialog(
     this,
     viewModel.startDateSetListener,
     // set DatePickerDialog to point to today's date when it loads up
     viewModel.calStart.get(Calendar.YEAR),
     viewModel.calStart.get(Calendar.MONTH),
     viewModel.calStart.get(Calendar.DAY_OF_MONTH)
 ).show()
}

fun projectEndDateClick(view: View) {

 DatePickerDialog(
     this,
     viewModel.endDateSetListener,
     // set DatePickerDialog to point to today's date when it loads up
     viewModel.calEnd.get(Calendar.YEAR),
     viewModel.calEnd.get(Calendar.MONTH),
     viewModel.calEnd.get(Calendar.DAY_OF_MONTH)
 ).show()
}

override fun onStarted() {
 Toast.makeText(this@ProjectAddActivity, "Kayıt İşlemi Başladı.", Toast.LENGTH_LONG).show()
}

override fun onSuccess() {
 Toast.makeText(this@ProjectAddActivity, "Başarılı!", Toast.LENGTH_LONG).show()
    startMainMenuActivity()
}

override fun onFailure(message: String) {
 Toast.makeText(this@ProjectAddActivity, message, Toast.LENGTH_LONG).show()
}


}

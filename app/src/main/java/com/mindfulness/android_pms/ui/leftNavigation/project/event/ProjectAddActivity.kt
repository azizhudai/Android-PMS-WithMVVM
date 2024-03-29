package com.mindfulness.android_pms.ui.leftNavigation.project.event

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.ProjectLog
import com.mindfulness.android_pms.databinding.ActivityProjectAddBinding
import com.mindfulness.android_pms.ui.auth.AuthListener
import com.mindfulness.android_pms.utils.startMainMenuActivity
import kotlinx.android.synthetic.main.activity_project_add.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS", "UNUSED_VALUE")
class ProjectAddActivity : AppCompatActivity(), AuthListener, KodeinAware,
    AdapterView.OnItemSelectedListener {

    override val kodein by kodein()
    private val factory: ProjectAddViewModelFactory by instance()
    private lateinit var viewModel: ProjectAddViewModel

    private var setStartYear: Int = 0
    private var setStartMonth: Int = 0
    private var setStartDay: Int = 0

    private var setEndYear: Int = 0
    private var setEndMonth: Int = 0
    private var setEndDay: Int = 0

    var dialogStartDate: DatePickerDialog? = null
    var dialogEndDate: DatePickerDialog? = null

    var pid: String? = null

    lateinit var rv_projectLog2: RecyclerView
    var adapter: ProjectLogRecyclerAdapter? = null
    var selectedTech = 0

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

        rv_projectLog2 = findViewById(R.id.rv_projectLog)

        //get intent values
        val intent1: Intent = intent
        pid = intent1.getStringExtra("pid")
        if (pid == null) {
            println("pid is empty." + pid)
            val listOfItemsTech = arrayOf("Kanban", "Divide of 4", "Divide by infinity")
            sp_tech.visibility = View.VISIBLE
            sp_tech!!.setOnItemSelectedListener(this@ProjectAddActivity)

            // Create an ArrayAdapter using a simple spinner layout and languages array
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsTech)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            sp_tech!!.adapter = aa
        }
        if (!pid.isNullOrEmpty()) {

            sp_tech.visibility = View.GONE
            if(rv_projectLog2.isNotEmpty()) {
                getDataFromFiretoreProjectLog(rv_projectLog2, pid!!)
            }

            viewModel.deneme(pid!!).observe(this, Observer { project ->
                if (project != null) {
                    et_projectTitle.setText(project.projectName)

                    sp_tech.setSelection(project.techId!!.toInt())
                    viewModel.selectedItemPositionSpinner = project.techId!!.toInt()
                    //Toast.makeText(this,project.techId.toString(),Toast.LENGTH_LONG).show()

                    ed_projectDetail.setText(project.projectDetail)
                    tv_ProjectStartDate.text = project.projectStartDate.let { "Start Date" }
                    project.projectStartDate?.let {

                        tv_ProjectStartDate.text = project.projectStartDate
                        var startDate = project.projectStartDate!!.split("/")

                        setStartDay = startDate[0].toInt()
                        setStartMonth = startDate[1].toInt()
                        setStartYear = startDate[2].toInt()
                        var datePicker = DatePicker(this)
                        viewModel.startDateSetListener.onDateSet(
                            datePicker,
                            setStartYear,
                            setStartMonth - 1,
                            setStartDay
                        )
                    }
                    tv_ProjectEndDate.text = project.projectEndDate.let { "End Date" }
                    project.projectEndDate?.let {
                        tv_ProjectEndDate.text = project.projectEndDate

                        val endDate = project.projectEndDate!!.split("/")

                        setEndDay = endDate[0].toInt()
                        setEndMonth = endDate[1].toInt()
                        setEndYear = endDate[2].toInt()
                        val datePicker = DatePicker(this)
                        viewModel.endDateSetListener.onDateSet(
                            datePicker,
                            setEndYear,
                            setEndMonth - 1,
                            setEndDay
                        )
                    }
                }
            })
        }

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

        /*var year: Int
        var month: Int
        var day: Int

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.calStart.set(Calendar.YEAR, setStartYear)
                viewModel.calStart.set(Calendar.MONTH, setStartMonth)
                viewModel.calStart.set(Calendar.DAY_OF_MONTH, setStartDay)
            }

        if (setStartYear == 0) {
            year = Calendar.YEAR
            month = Calendar.MONTH
            day = Calendar.DAY_OF_MONTH
            println("YEAR:" + Calendar.YEAR + "::month::" + month)
            var aa = viewModel.startDateSetListener
        } else {
            year = setStartYear
            month = setStartMonth
            day = setStartDay
            var aa = dateSetListener
        }*/

        dialogStartDate = DatePickerDialog(
            this,
            viewModel.startDateSetListener,//viewModel.startDateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            viewModel.calStart.get(Calendar.YEAR),
            viewModel.calStart.get(Calendar.MONTH),
            viewModel.calStart.get(Calendar.DAY_OF_MONTH)
        )
        dialogStartDate!!.show()

        //val c = Calendar.getInstance()

        /*var day2 = 0
        var month2 = 0
        var year2 = 0
        if (setStartYear == 0) {
            day2 = viewModel.calStart.get(Calendar.DAY_OF_MONTH)
            month2 = viewModel.calStart.get(Calendar.MONTH)
            year2 = viewModel.calStart.get(Calendar.YEAR)
            println("date:::$setStartDay - $setStartMonth - $setStartYear")
        } else {
            day2 = setStartYear
            month2 = setStartMonth
            year2 = setStartDay
            println("date:::$setStartDay - $setStartMonth - $setStartYear")
        }
        val dpd = DatePickerDialog(
            this,
            android.R.style.Widget_PopupWindow,
            DatePickerDialog.OnDateSetListener { datePicker, selyear, monthOfYear, dayOfMonth ->

                if (tv_ProjectEndDate.text.equals("End Date")) {
                    viewModel.setStartDate(selyear, monthOfYear, dayOfMonth)
                } else { // Son tarihten küçük mü kontrol et

                    viewModel.checkStartDate(selyear, monthOfYear, dayOfMonth)

                }
                day2 = dayOfMonth
                month2 = monthOfYear + 1
                year2 = selyear
                //tv.text = "$day - $month - $year"

            }, year2, month2, day2
        )

        dpd.show()*/

    }

    fun projectEndDateClick(view: View) {

        dialogEndDate = DatePickerDialog(
            this,
            viewModel.endDateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            viewModel.calEnd.get(Calendar.YEAR),
            viewModel.calEnd.get(Calendar.MONTH),
            viewModel.calEnd.get(Calendar.DAY_OF_MONTH)
        )
        // dialog.getDatePicker().setOnDateChangedListener().setMinDate(System.currentTimeMillis() - 1000)
        // dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dialogEndDate!!.show()
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

    fun insertOrUpdateProjectClick(view: View) {

        if (pid.isNullOrEmpty()) {
            viewModel.insertClick()
        } else {
            viewModel.insertClick(pid)
        }
    }

    private fun getDataFromFiretoreProjectLog(rv_projectLog: RecyclerView, projectId: String) {

        val db = FirebaseFirestore.getInstance()
        val projectLogDoc = db.collection("ProjectLog")
        val query = projectLogDoc.whereEqualTo(
            "projectId",
            projectId
        )//.orderBy("createDate",Query.Direction.DESCENDING) //.orderBy("createDate",Query.Direction.DESCENDING)
        /*  var query = viewModel._getProjectLogDB(projectId)
          query?.observe(this, Observer {
              if(it != null){*/
        val options =
            FirestoreRecyclerOptions.Builder<ProjectLog>().setQuery(query, ProjectLog::class.java)
                .build()
        adapter = ProjectLogRecyclerAdapter(options = options)
        rv_projectLog.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this@ProjectAddActivity.applicationContext)
        rv_projectLog.layoutManager = layoutManager
        rv_projectLog.itemAnimator = null
        rv_projectLog.adapter = adapter
        // }

        //  })


    }

    override fun onStart() {
        super.onStart()
        if (adapter != null)
            adapter!!.startListening()
    }

    override fun onResume() {
        super.onResume()
        if (adapter != null)
            adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null)
            adapter!!.stopListening()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        viewModel.selectedItemPositionSpinner = position
        selectedTech = position
        // Toast.makeText(application,selectedTech.toString(),Toast.LENGTH_LONG).show()
    }

    /*fun setSelectedItemPosition(selectedItemPosition: Int) {

        // var BR :Spinner
        notifyPropertyChanged(sp_tech.selectedItemPosition)
    }*/
}

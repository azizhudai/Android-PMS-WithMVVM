package com.mindfulness.android_pms.ui.leftNavigation.project.event

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.repositories.ProjectRepository
import com.mindfulness.android_pms.ui.auth.AuthListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ProjectAddViewModel(
    private val repository: ProjectRepository
) : ViewModel() {

    var project: Project? = null
    var title: String? = null
    var projectDetail: String? = null
    var startDate: String? = "Start Date"
    var endDate: String? = "End Date"
    var status = MutableLiveData<String?>()
    var startDateLiveData = MutableLiveData<String>()
    var endDateLiveData = MutableLiveData<String>()
    var startStatusLiveData = MutableLiveData<String>()
    var endStatusLiveData = MutableLiveData<String>()

    val calStart = Calendar.getInstance()
    val calEnd = Calendar.getInstance()

    var startYear = 0
    var startMonth = 0
    var startDay = 0
    var endYear = 0
    var endMonth = 0
    var endDay = 0


    //auth listener
    var authListener: AuthListener? = null
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    fun insertClick() {
        //validating email and password
        if (title.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Title")
            return
        }

        //insert started
        authListener?.onStarted()

        project = Project("", title!!, projectDetail,startDateLiveData.value,endDateLiveData.value,Calendar.getInstance().time.toString(),"")
        //calling login from repository to perform the actual inserteion
        val disposable = repository.projectInsert(project!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                try {
                    authListener?.onSuccess()
                } catch (e: Exception) {
                    authListener?.onFailure("Başarısız!")
                }

            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)

    }


    fun setStartDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calStart.set(Calendar.YEAR, year)
        calStart.set(Calendar.MONTH, monthOfYear)
        calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        startYear = year
        startMonth = monthOfYear
        startDay = dayOfMonth

        updateStartDateInView()
    }

    fun setEndDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calEnd.set(Calendar.YEAR, year)
        calEnd.set(Calendar.MONTH, monthOfYear)
        calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        endYear = year
        endMonth = monthOfYear
        endDay = dayOfMonth

        updateEndDateInView()
    }

    private fun updateStartDateInView() {
        // dil seçeneğine göre değiştir
        //tr
        val myFormat = "dd/MM/yyyy" // mention the format you need
        //us
        //val myFormat = "MM/dd//yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        startDateLiveData.value = sdf.format(calStart.getTime())
        startDate = sdf.format(calStart.getTime())
    }

    private fun updateEndDateInView() {
        // dil seçeneğine göre değiştir
        //tr
        val myFormat = "dd/MM/yyyy" // mention the format you need
        //us
        //val myFormat = "MM/dd//yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        endDateLiveData.value = sdf.format(calEnd.getTime())
        endDate = sdf.format(calEnd.getTime())

    }


    // create an OnDateSetListener
    val startDateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {


            if (endDate.equals("End Date")) {
                setStartDate(year, monthOfYear, dayOfMonth)

            } else { // Son tarihten küçük mü kontrol et

                checkStartDate(year, monthOfYear, dayOfMonth)

            }
        }
    }

    val endDateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {

            if (startDate.equals("Start Date")) {
                setEndDate(year, monthOfYear, dayOfMonth)

            } else { // ilk tarihten büyük mü kontrol et

                checkEndDate(year, monthOfYear, dayOfMonth)

            }

        }
    }

    ////

    fun checkStartDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        var isSetStartDate: Boolean? = null

        // yıl kontrolü :::
        if (endYear < year) {
            isSetStartDate = false
            startStatusLiveData.value =
                "Başlama yılı Bitirme yılından küçük olamaz: ${endYear} >= ${year}"
            /* Toast.makeText(
                 this@ProjectAddActivity,
                 "Başlama yılı Bitirme yılından küçük olamaz: ${endYear} >= ${year}",
                 Toast.LENGTH_SHORT
             ).show()*/

        }
        if (endYear > year) {
            isSetStartDate = true
        }
        // :::

        if (endMonth == monthOfYear && endYear == year && endDay >= dayOfMonth) {
            isSetStartDate = true
        }
        // ay kontrolü ...
        if (startYear == year && endMonth < monthOfYear) {
            isSetStartDate = false
            startStatusLiveData.value =
                "Başlama ayı Bitirme ayından küçük olamaz: ${endMonth} > ${monthOfYear}"
            /* Toast.makeText(
                 this@ProjectAddActivity,
                 "Başlama ayı Bitirme ayından küçük olamaz: ${endMonth} > ${monthOfYear}",
                 Toast.LENGTH_SHORT
             ).show()*/

        }
        if (endYear == year && endMonth >= monthOfYear) {
            isSetStartDate = true
        }
        //...

        // gün kontrolü
        if (endMonth == monthOfYear && endYear == year && endDay < dayOfMonth) {
            isSetStartDate = false
            startStatusLiveData.value =
                "Başlama günü Bitirme gününden küçük olamaz: ${startDay} >= ${dayOfMonth}"
            /*Toast.makeText(
                this@ProjectAddActivity,
                "Başlama günü Bitirme gününden küçük olamaz: ${startDay} >= ${dayOfMonth}",
                Toast.LENGTH_SHORT
            ).show()*/

        }
        if (isSetStartDate != null && isSetStartDate == true) {
            setStartDate(year, monthOfYear, dayOfMonth)
        }

    }


    fun checkEndDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        var isSetEndDate: Boolean? = null

        // yıl kontrolü :::
        if (startYear > year) {
            isSetEndDate = false
            endStatusLiveData.value =
                "Bitirme yılı Başlama yılından küçük olamaz: ${startYear} >= ${year}"
            /*Toast.makeText(
                this@ProjectAddActivity,
                "Bitirme yılı Başlama yılından küçük olamaz: ${startYear} >= ${year}",
                Toast.LENGTH_SHORT
            ).show()*/

        }
        if (startYear < year) {
            isSetEndDate = true
        }
        // :::

        if (startMonth == monthOfYear && startYear == year && startDay <= dayOfMonth) {
            isSetEndDate = true
        }
        // ay kontrolü ...
        if (startYear == year && startMonth > monthOfYear) {
            isSetEndDate = false
            endStatusLiveData.value =
                "Bitirme ayı Başlama ayından küçük olamaz: ${startMonth} > ${monthOfYear}"
            /*Toast.makeText(
                this@ProjectAddActivity,
                "Bitirme ayı Başlama ayından küçük olamaz: ${startMonth} > ${monthOfYear}",
                Toast.LENGTH_SHORT
            ).show()*/

        }
        if (startYear == year && startMonth <= monthOfYear) {
            isSetEndDate = true
        }
        //...

        // gün kontrolü
        if (startMonth == monthOfYear && startYear == year && startDay > dayOfMonth) {
            isSetEndDate = false
            endStatusLiveData.value ="Bitirme günü Başlama gününden küçük olamaz: ${startDay} >= ${dayOfMonth}"
           /* Toast.makeText(
                this@ProjectAddActivity,
                "Bitirme günü Başlama gününden küçük olamaz: ${startDay} >= ${dayOfMonth}",
                Toast.LENGTH_SHORT
            ).show()*/


        }
        if (isSetEndDate != null && isSetEndDate == true) {
            setEndDate(year, monthOfYear, dayOfMonth)
        }

    }

    ////
//disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
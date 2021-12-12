package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.pojo.TaskDivideCard
import com.mindfulness.android_pms.data.repositories.TaskRepository
import com.mindfulness.android_pms.ui.auth.AuthListener
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class DivideInfiniteViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    var authListener: AuthListener? = null
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    // variable to track event time
    private var mLastClickTime: Long = 0

    /*  private val _taskDivideCard=MutableLiveData<Query>().apply {

         //return repository.getTaskDivideCard(projectId)



          val db: FirebaseFirestore = FirebaseFirestore.getInstance()

          val query = db.collection("TaskDivideCard")
          value = query

      }*/

    /* private fun _taskCardList(projectId:String) = MutableLiveData<Query>().apply {
         val db:FirebaseFirestore = FirebaseFirestore.getInstance()
         var query = db.collection("TaskDivideCard").whereEqualTo(
             "projectId",
             projectId
         )
         value = query
     }*/
    //val taskDivideCard:LiveData<Query> = _taskDivideCard

    var cardTitle: String? = null
    var cardDetail: String? = null

    private fun _taskCardInsert(CardClass:TaskDivideCard)= MutableLiveData<Completable>().apply {
        value = repository.setCardInsert(CardClass)
    }

    private fun _taskCardListRep(projectId: String) = MutableLiveData<Query>().apply {

        value = repository.getTaskInfCardList(projectId)
    }

    fun insertCardClick(cid: String? = null,pid:String) {

        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        //validating email and password
        if (cardTitle.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Title")
            return
        }

        //insert started
        authListener?.onStarted()

        // if(pid == null){
        val card =TaskDivideCard(
            cid,
            "uid",
            pid,
            cardTitle!!,
            cardDetail,
            0
        )
        // }
        /* if(pid != null){
             project = Project(
                 pid,
                 title!!,
                 projectDetail,
                 startDateLiveData.value,
                 endDateLiveData.value,
                 Calendar.getInstance().time.toString(),
                 ""
             )
         }*/
        card.cardId = cid

        //calling login from repository to perform the actual insertion
        val disposable = repository.setCardInsert(card)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                try {
                    authListener?.onSuccess()
                } catch (e: Exception) {
                    authListener?.onFailure("Başarısız!")
                }
                /*finally {
                    authListener?.onFailure("Başarısız!")
                }*/

            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)

    }


    fun taskCardList(projectId: String): LiveData<Query> = _taskCardListRep(projectId = projectId)

   // fun taskCardInsert(CardClass:TaskDivideCard): LiveData<Completable> = _taskCardInsert(CardClass)

    fun getExistCard(cid: String): MutableLiveData<TaskDivideCard?> {
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var card: MutableLiveData<TaskDivideCard?> = MutableLiveData()

        db.collection("TaskDivideCard").whereEqualTo("cardId", cid).get()
            .addOnSuccessListener { documents ->

                if (documents != null) {

                    var i = 0
                    for (document in documents) {
                        //  projectStr.add(1,pro)
                        card.value = TaskDivideCard(
                            document.get("cardId") as String?,
                            document.get("userId") as String,
                            document.get("projectId") as String,
                            document.get("cardTitle") as String,
                            document.get("cardDetail") as String?,
                            document.get("taskCound") as Long?
                        )

                        i++
                    }
                }

            }
        return card
    }


}
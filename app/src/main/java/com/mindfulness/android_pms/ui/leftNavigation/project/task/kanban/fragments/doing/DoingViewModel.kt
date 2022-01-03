package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.doing

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DoingViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun query(projectId:String): MutableLiveData<Query> = MutableLiveData<Query>().apply {
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        var projectDoc = db.collection("TaskKanban")
        var query = projectDoc.orderBy(
            "taskCreateDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("projectId", projectId)
        query = query.whereEqualTo("taskStatus", "2")
        value =query
    }

}

package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindfulness.android_pms.data.repositories.TaskRepository
import java.util.*

class DivideInfiniteViewModel(
    private val repository: TaskRepository
):ViewModel() {

    val text34 = "aaaa"

    private val _taskDivideCard=MutableLiveData<Query>().apply {

       //return repository.getTaskDivideCard(projectId)



        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val query = db.collection("TaskDivideCard")
        value = query

    }
    val taskDivideCard:LiveData<Query> = _taskDivideCard
}
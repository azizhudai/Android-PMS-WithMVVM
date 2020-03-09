package com.mindfulness.android_pms.data.repositories
import com.mindfulness.android_pms.data.firebase.FirebaseSource

class TaskRepository(private val firebase:FirebaseSource) {

    fun getTaskDivideCard(projectId: String) = firebase.getTaskDivideCard(projectId)

}
package com.mindfulness.android_pms.data.repositories
import com.mindfulness.android_pms.data.firebase.FirebaseSource
import com.mindfulness.android_pms.data.pojo.TaskDivideCard

class TaskRepository(private val firebase:FirebaseSource) {

    fun getTaskDivideCard(projectId: String) = firebase.getTaskDivideCard(projectId)

    fun getTaskInfCardList(projectId: String) = firebase.getTaskInfCardList(projectId)

    fun setCardInsert(CardClass: TaskDivideCard) = firebase.setCardInsert(CardClass)

}
package com.mindfulness.android_pms.data.repositories

import com.mindfulness.android_pms.data.firebase.FirebaseSource
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.pojo.TaskKanban

class ProjectRepository(private val firebase: FirebaseSource) {

    fun projectInsert(project: Project) = firebase.projectInsert(project)

    fun userProjectList(userId: String) = firebase.userProjectList(userId)

    fun getProjectLogOptions(projectId: String) = firebase.getProjectLogList(projectId)

    fun addKanbanTask(task:TaskKanban) = firebase.addKanbanTask(task)
}
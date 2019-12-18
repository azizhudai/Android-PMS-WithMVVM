package com.mindfulness.android_pms.ui.leftNavigation.project.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindfulness.android_pms.data.repositories.ProjectRepository

@Suppress("UNCHECKED_CAST")
class ProjectAddViewModelFactory(
    private val repository: ProjectRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectAddViewModel(repository) as T
    }
}
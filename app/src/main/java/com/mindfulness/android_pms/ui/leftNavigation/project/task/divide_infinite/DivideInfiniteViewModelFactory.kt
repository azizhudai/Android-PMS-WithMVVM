package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindfulness.android_pms.data.repositories.TaskRepository

@Suppress("UNCHECKED_CAST")
class DivideInfiniteViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DivideInfiniteViewModel(repository) as T
    }
}
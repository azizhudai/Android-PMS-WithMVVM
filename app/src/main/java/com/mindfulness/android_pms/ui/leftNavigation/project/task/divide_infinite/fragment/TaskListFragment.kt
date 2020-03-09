package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.mindfulness.android_pms.R

@Suppress("DEPRECATION")
class TaskListFragment(val projectId:String) : Fragment() {

    companion object {
        fun newInstance(projectId: String) = TaskListFragment(projectId = projectId)
    }

    private lateinit var viewModel: TaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TaskListViewModel::class.java)
        // TODO: Use the ViewModel
        Toast.makeText(
            this@TaskListFragment.context,
            "id: aaa",
            Toast.LENGTH_LONG
        ).show()
    }

}

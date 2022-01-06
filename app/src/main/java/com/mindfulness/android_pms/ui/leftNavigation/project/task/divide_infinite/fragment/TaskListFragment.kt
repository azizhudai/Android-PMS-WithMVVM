package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.pojo.Task
import com.mindfulness.android_pms.ui.leftNavigation.project.ProjectRecyclerAdapter
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_4.DivideMainActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.DivideInfiniteMainActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.kanban.TaskManagementActivity

@Suppress("DEPRECATION")
class TaskListFragment(val projectId: String) : Fragment() {

    companion object {
        fun newInstance(projectId: String) = TaskListFragment(projectId = projectId)
    }

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskRecyclerAdapter
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var taskDoc = db.collection("Task")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.task_list_fragment, container, false)
        val rvTaskList: RecyclerView = root.findViewById(R.id.rv_task_list)
        //rv_project.itemAnimator = object :FaceDetector.Fa
        getDataFromFirestore(rvTaskList)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TaskListViewModel::class.java)
        // TODO: Use the ViewModel
        Toast.makeText(
            this@TaskListFragment.context,
            projectId,
            Toast.LENGTH_LONG
        ).show()

    }

    private fun getDataFromFirestore(rv_project: RecyclerView) {

        val query = taskDoc/*.orderBy(
            "projectCreateDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("createUserId", firebaseAuth.uid)
*/
        var options =
            FirestoreRecyclerOptions.Builder<Task>().setQuery(query, Task::class.java).build()
        adapter = TaskRecyclerAdapter(options = options, mCtx = context!!)

        rv_project.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context!!.applicationContext)
        rv_project.layoutManager = layoutManager
        rv_project.itemAnimator = null
        rv_project.adapter = adapter

        if(this::adapter.isInitialized)
        adapter.setOnItemClickListener(object : TaskRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {

                val task = documentSnapshot.toObject(Task::class.java)
                val id = documentSnapshot.id
                //val techId = task?.techId.let { project?.techId.toString().toInt() } //documentSnapshot.get("techId").toString().toInt() //as? Int ?: -1
                /*Toast.makeText(
                    context,
                    "id: $id",
                    Toast.LENGTH_LONG
                ).show()*/

                /*Intent(activity, TaskManagementActivity::class.java).also { itt ->
                    //MainMenuActivity
                    //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    itt.putExtra("pid", id)
                    itt.putExtra("pname", project!!.projectName)
                    startActivity(itt)
                }*/
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if(this::adapter.isInitialized)
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(this::adapter.isInitialized)
            adapter.stopListening()
    }

}

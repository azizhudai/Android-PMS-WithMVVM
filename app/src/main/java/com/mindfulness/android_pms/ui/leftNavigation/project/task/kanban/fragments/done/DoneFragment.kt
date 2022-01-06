package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.done

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.TaskKanban
import com.mindfulness.android_pms.ui.leftNavigation.project.task.kanban.TaskManagementActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.adapter.TaskKanbanRecyclerAdapter

class DoneFragment : Fragment() {

    lateinit var projectId: String

    companion object {
        const val PID = "pid"

       /* fun newInstance(pid: String): DoneFragment {
            val fragment = DoneFragment()

            val bundle = Bundle().apply {
                putString("pid", pid)
                println("namenamename$pid")
            }

            fragment.arguments.let { bundle }

            return fragment
        }*/
    }

    private lateinit var viewModelTask: DoneViewModel
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var projectDoc = db.collection("TaskKanban")
    var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapterTaskKanban: TaskKanbanRecyclerAdapter
    var root: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.done_fragment, container, false)

        viewModelTask = ViewModelProviders.of(this).get(DoneViewModel::class.java)
        // TODO: Use the ViewModel

        val projectId = arguments!!.getString("pid")
        println("projectIdFragment: ${arguments?.getString("pid")}")

        val rvTaskDone: RecyclerView = root!!.findViewById(R.id.rv_done)
        getDataFromFirestore(rvTaskDone,projectId)

        setRecyclerViewItemTouchListener(rvTaskDone)

        return root
    }

    private fun getDataFromFirestore(rvTaskDone: RecyclerView,projectId:String?) {

        var query = projectDoc.whereEqualTo("projectId", projectId)
        query = query.whereEqualTo("taskStatus", "3")
//.orderBy(
//            "taskCreateDate",
//            Query.Direction.DESCENDING
//        )
        var options =
            FirestoreRecyclerOptions.Builder<TaskKanban>().setQuery(query, TaskKanban::class.java)
                .build()
        adapterTaskKanban = TaskKanbanRecyclerAdapter(options = options, mCtx = context!!)

        rvTaskDone.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context!!.applicationContext)
        rvTaskDone.layoutManager = layoutManager
        rvTaskDone.itemAnimator = null
        rvTaskDone.adapter = adapterTaskKanban

        if(this::adapterTaskKanban.isInitialized)
        adapterTaskKanban.setOnItemClickListener(object :
            TaskKanbanRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {

                var task = documentSnapshot.toObject(TaskKanban::class.java)
                var id = documentSnapshot.id
               /* Toast.makeText(
                    context,
                    "id: $id",
                    Toast.LENGTH_LONG
                ).show()*/
                Intent(activity, TaskManagementActivity::class.java).also { itt ->
                    //MainMenuActivity
                    //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    itt.putExtra("tid", id)
                    itt.putExtra("tname", task!!.taskTitle)
                    startActivity(itt)
                }
            }
        })
    }



    private fun setRecyclerViewItemTouchListener(rvDone: RecyclerView) {
        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                /*   val position_dragged = viewHolder.adapterPosition
                   val position_target = viewHolder1.adapterPosition

                   Collections.swap(projectList!!, position_dragged, position_target)
                   adapter!!.notifyItemMoved(position_dragged, position_target)*/
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                //adapter!!.deleteItem(position)
                //adapter!!.notifyItemRemoved(position)

                println("swipeDir:: $swipeDir")
                if (swipeDir == 4) {
                    //sola sürüklendi status 1 olacak
                    // rvDone.setBackgroundColor(Color.BLUE)
                    adapterTaskKanban.changeStatus("2", position)
                }

            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvDone)
    }

    override fun onStart() {
        super.onStart()
        if(this::adapterTaskKanban.isInitialized)
        adapterTaskKanban.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(this::adapterTaskKanban.isInitialized)
            adapterTaskKanban.stopListening()
    }
}

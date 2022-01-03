package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.todo

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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.TaskKanban
import com.mindfulness.android_pms.ui.leftNavigation.project.task.kanban.TaskManagementActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.adapter.TaskKanbanRecyclerAdapter

class TodoFragment : Fragment() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var projectDoc = db.collection("TaskKanban")
    var adapterTaskKanban: TaskKanbanRecyclerAdapter? = null
    var root: View? = null

    private lateinit var viewModelTodo: TodoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.todo_fragment, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelTodo = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        // TODO: Use the ViewModel

        val projectId = arguments?.getString("pid")
        //println("projectIdFragment: ${arguments?.getString("pid")}")

        val rvTaskDoing: RecyclerView = root!!.findViewById(R.id.rv_todo)
        getDataFromFirestore(rvTaskDoing, projectId!!)

        setRecyclerViewItemTouchListener(rvTaskDoing)
    }

    private fun getDataFromFirestore(rvTaskDoing: RecyclerView, projectId: String?) {

        var query = projectDoc.whereEqualTo("projectId", projectId)/*.orderBy(
            "taskCreateDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("projectId", projectId)*/
        query = query.whereEqualTo("taskStatus", "1")

        //var queryy = viewModelDoing.query(projectId!!).value
        var options =
            FirestoreRecyclerOptions.Builder<TaskKanban>().setQuery(query, TaskKanban::class.java)
                .build()
        adapterTaskKanban = TaskKanbanRecyclerAdapter(options = options, mCtx = context!!)

        rvTaskDoing.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context!!.applicationContext)
        rvTaskDoing.layoutManager = layoutManager
        rvTaskDoing.adapter = adapterTaskKanban

        adapterTaskKanban!!.setOnItemClickListener(object :
            TaskKanbanRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {

                //var task = documentSnapshot.toObject(TaskKanban::class.java)
                var id = documentSnapshot.id
                Toast.makeText(
                    context,
                    "id: $id",
                    Toast.LENGTH_LONG
                ).show()
                Intent(activity, TaskManagementActivity::class.java).also { itt ->
                    //MainMenuActivity
                    //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    itt.putExtra("tid", id)
                    //itt.putExtra("tname", task!!.taskTitle)
                    startActivity(itt)
                }
            }
        })
    }

    private fun setRecyclerViewItemTouchListener(rvTodo: RecyclerView) {
        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                 ItemTouchHelper.RIGHT //or ItemTouchHelper.DOWN
            ) {

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                // rvDone.setBackgroundColor(Color.RED)
                println("actionState:$actionState")
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                rvTodo.setBackgroundColor(0)
            }

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

            override fun isItemViewSwipeEnabled(): Boolean {
                return true //super.isItemViewSwipeEnabled()
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                //adapter!!.deleteItem(position)
                //adapter!!.notifyItemRemoved(position)


                if (swipeDir == 8) {
                    //sağ sürüklendi status 3 olacak
                    //rvDone.setBackgroundColor(Color.GREEN)
                    adapterTaskKanban!!.changeStatus("2", position)
                }
            }

            override fun onMoved(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                fromPos: Int,
                target: RecyclerView.ViewHolder,
                toPos: Int,
                x: Int,
                y: Int
            ) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                println("x:$x Y:$y")
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvTodo)


    }

    override fun onStart() {
        super.onStart()
        adapterTaskKanban!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapterTaskKanban != null)
            adapterTaskKanban!!.stopListening()
    }


}

package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.doing

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
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.add.TaskKanbanAddActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.adapter.TaskKanbanRecyclerAdapter

class DoingFragment : Fragment() {

    //private lateinit var viewModelTask: DoneViewModel
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var projectDoc = db.collection("TaskKanban")

    // var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapterTaskKanban: TaskKanbanRecyclerAdapter
    var root: View? = null

    private lateinit var viewModelDoing: DoingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.doing_fragment, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelDoing = ViewModelProviders.of(this).get(DoingViewModel::class.java)
        // TODO: Use the ViewModel

        val projectId = arguments?.getString("pid")
        //println("projectIdFragment: ${arguments?.getString("pid")}")

        val rvTaskDoing: RecyclerView = root!!.findViewById(R.id.rv_doing)
        getDataFromFirestore(rvTaskDoing, projectId!!)

        setRecyclerViewItemTouchListener(rvTaskDoing)
    }

    private fun getDataFromFirestore(rvTaskDoing: RecyclerView, projectId: String?) {

        var query = projectDoc.whereEqualTo("projectId", projectId)/*.orderBy(
            "taskCreateDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("projectId", projectId)*/
        query = query.whereEqualTo("taskStatus", "2")

        //var queryy = viewModelDoing.query(projectId!!).value
        println("projectId22:$projectId")
        var options =
            FirestoreRecyclerOptions.Builder<TaskKanban>().setQuery(query, TaskKanban::class.java)
                .build()
        adapterTaskKanban = TaskKanbanRecyclerAdapter(options = options, mCtx = context!!)

        rvTaskDoing.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context!!.applicationContext)
        rvTaskDoing.layoutManager = layoutManager
        rvTaskDoing.itemAnimator = null
        rvTaskDoing.adapter = adapterTaskKanban

        if (this::adapterTaskKanban.isInitialized)
            adapterTaskKanban.setOnItemClickListener(object :
                TaskKanbanRecyclerAdapter.OnItemClickListener {
                override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {

                    //var task = documentSnapshot.toObject(TaskKanban::class.java)
                    val id = documentSnapshot.id
                    /*  Toast.makeText(
                          context,
                          "id: $id",
                          Toast.LENGTH_LONG
                      ).show()*/
                    Intent(activity, TaskKanbanAddActivity::class.java).also { itt ->
                        //MainMenuActivity
                        //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        itt.putExtra("tid", id)
                        //itt.putExtra("tname", task!!.taskTitle)
                        startActivity(itt)
                    }
                }
            })
    }

    private fun setRecyclerViewItemTouchListener(rvDone: RecyclerView) {
        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT //or ItemTouchHelper.DOWN
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
                rvDone.setBackgroundColor(0)
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

                println("swipeDir:: $swipeDir")
                if (swipeDir == 4) {
                    //sola sürüklendi status 1 olacak
                    // rvDone.setBackgroundColor(Color.BLUE)
                    adapterTaskKanban.changeStatus("1", position)
                }
                if (swipeDir == 8) {
                    //sağ sürüklendi status 3 olacak
                    //rvDone.setBackgroundColor(Color.GREEN)
                    adapterTaskKanban.changeStatus("3", position)
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
        itemTouchHelper.attachToRecyclerView(rvDone)


    }

    override fun onStart() {
        super.onStart()
        if (this::adapterTaskKanban.isInitialized)
            adapterTaskKanban.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (this::adapterTaskKanban.isInitialized)
            adapterTaskKanban!!.stopListening()
    }

}


/*
 val builder = AlertDialog.Builder(activity)
                //set title for alert dialog
                builder.setTitle("Delete Status")
                //set message for alert dialog
                // var message = it.get("pname") as String
                builder.setMessage(R.string.dialogTitle)
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    adapterTaskKanban!!.deleteItem(position)
                    rvDone.setBackgroundColor(0)
                }
                //performing cancel action
                builder.setNeutralButton("Cancel") { _, _ ->
                    adapterTaskKanban!!.notifyItemRemoved(position)
                    adapterTaskKanban!!.notifyItemInserted(position)
                }
                //performing negative action
                builder.setNegativeButton("No") { _, _ ->
                    adapterTaskKanban!!.notifyItemRemoved(position)
                    /* mListItems.add(mRecentlyDeletedItemPosition,
                         mRecentlyDeletedItem);*/
                    adapterTaskKanban!!.notifyItemInserted(position)
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
* */
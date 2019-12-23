package com.mindfulness.android_pms.ui.leftNavigation.project

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.ui.leftNavigation.project.event.ProjectAddActivity
import java.util.*
import kotlin.collections.ArrayList


class ProjectFragment : Fragment() {

    private lateinit var projectViewModel: ProjectViewModel
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var projectDoc = db.collection("Project")
    var firebaseAuth = FirebaseAuth.getInstance()

    //private lateinit var db: FirebaseFirestore
    //private val projectNameArray: ArrayList<String> = ArrayList()
    var adapter: ProjectRecyclerAdapter? = null
    var projectList: MutableList<Project?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //projectNameArray.clear()
        projectViewModel =
            ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_project, container, false)

        val rv_project: RecyclerView = root.findViewById(R.id.rv_project)
        //rv_project.itemAnimator = object :FaceDetector.Fa
        getDataFromFirestore(rv_project)

        adapter!!.editClickStatus.observe(viewLifecycleOwner, Observer {
            if (it.get("statu") == true) {
                Log.e("ddd", "deneme " + it.get("pid"))
                Intent(activity, ProjectAddActivity::class.java).also { itt ->
                    //MainMenuActivity
                    //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    itt.putExtra("pid", it.get("pid") as String)
                    startActivity(itt)
                }
            }
        })

        adapter!!.deleteClickStatus.observe(viewLifecycleOwner, Observer {
            if (it.get("statu") == true) {

                val builder = AlertDialog.Builder(activity)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                //set message for alert dialog
                var message = it.get("pname") as String
                builder.setMessage(message)
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    //Toast.makeText(activity, "clicked yes", Toast.LENGTH_LONG).show()

                    var position = it.get("position") as Int
                    adapter!!.deleteItem(position)
                    /*var db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    db.collection("Project").document(it.get("pid") as String)
                        .delete()
                        .addOnSuccessListener {

                            // adapter!!.removeItem(position)

                            Log.d(
                                TAG,
                                "DocumentSnapshot successfully deleted!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
*/
                }
                //performing cancel action
                builder.setNeutralButton("Cancel") { _, _ ->

                }
                //performing negative action
                builder.setNegativeButton("No") { _, _ ->
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()


            }
        })

        setRecyclerViewItemTouchListener(rv_project)

        return root
    }

    private fun getDataFromFirestore(rv_project: RecyclerView) {


        var query = projectDoc.orderBy(
            "projectCreateDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("createUserId", firebaseAuth.uid)

        /*var aaa = query.get().addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful) {
task.getResult()
            }
            //   task.getResult(Project::class.java)
        }*/
        var options =
            FirestoreRecyclerOptions.Builder<Project>().setQuery(query, Project::class.java).build()
        adapter = ProjectRecyclerAdapter(options = options, mCtx = context!!)
        var project: Project = Project()
        //projectList.addAll(options.snapshots.toArray(arrayOf(project)))//.onEach { project ->
        /*   project.copy(project.projectId,project.projectName)
       })*/
        projectList.add(0, project.copy("1111", "sdad", "", "", "", "", ""))
        projectList.add(0, project.copy("111122", "sdad222", "", "", "", "", ""))

        rv_project.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context!!.applicationContext)
        rv_project.layoutManager = layoutManager
        rv_project.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    /* override fun onResume() {
         super.onResume()
         adapter!!.startListening()
     }*/

    override fun onStop() {
        super.onStop()
        if (adapter != null)
            adapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter!!.startListening()
        adapter!!.notifyDataSetChanged()
    }


    private fun setRecyclerViewItemTouchListener(rv_project: RecyclerView) {

        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
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

                val builder = AlertDialog.Builder(activity)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                //set message for alert dialog
                // var message = it.get("pname") as String
                builder.setMessage("Delete Status")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    adapter!!.deleteItem(position)
                }
                //performing cancel action
                builder.setNeutralButton("Cancel") { _, _ ->
                    adapter!!.notifyItemRemoved(position)
                    adapter!!.notifyItemInserted(position)
                }
                //performing negative action
                builder.setNegativeButton("No") { _, _ ->
                    adapter!!.notifyItemRemoved(position)
                    /* mListItems.add(mRecentlyDeletedItemPosition,
                         mRecentlyDeletedItem);*/
                    adapter!!.notifyItemInserted(position)
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()


            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_project)
    }

}

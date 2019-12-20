package com.mindfulness.android_pms.ui.leftNavigation.project

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.ui.leftNavigation.project.event.ProjectAddActivity
import com.mindfulness.android_pms.utils.startProjectAddActivity


class ProjectFragment : Fragment() {

    private lateinit var projectViewModel: ProjectViewModel

    //private lateinit var db: FirebaseFirestore
    private val projectNameArray: ArrayList<String> = ArrayList()
    var adapter: ProjectRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //projectNameArray.clear()
        projectViewModel =
            ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_project, container, false)


        // val textView: TextView = root.findViewById(R.id.text_home)
        /* projectViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })
 */
        //// recyclerview

        val rv_project: RecyclerView = root.findViewById(R.id.rv_project)
        //db = FirebaseFirestore.getInstance()
        //getDataFromFirestore()

        var layoutManager = LinearLayoutManager(requireContext())
        rv_project.layoutManager = layoutManager


////
        projectViewModel.projectList.observe(viewLifecycleOwner, Observer {
            adapter = activity?.let { it1 -> ProjectRecyclerAdapter(it, it1) }
            rv_project.adapter = adapter
            adapter!!.notifyDataSetChanged()

            adapter!!.editClickStatus.observe(viewLifecycleOwner, Observer {
                if (it.get("statu") == true) {
                    Log.e("ddd", "deneme " + it.get("pid"))
                    Intent(activity, ProjectAddActivity::class.java).also { itt ->
                        //MainMenuActivity
                        //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        itt.putExtra("pid",  it.get("pid") as String)
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
                        Toast.makeText(activity, "clicked yes", Toast.LENGTH_LONG).show()

                        var position = it.get("position") as Int
                        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
                        db.collection("Project").document(it.get("pid") as String)
                            .delete()
                            .addOnSuccessListener {

                                adapter!!.removeItem(position)

                                Log.d(
                                    TAG,
                                    "DocumentSnapshot successfully deleted!"
                                )
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                    }
                    //performing cancel action
                    builder.setNeutralButton("Cancel") { dialogInterface, which ->
                        Toast.makeText(
                            activity,
                            "clicked cancel\n operation cancel",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    //performing negative action
                    builder.setNegativeButton("No") { dialogInterface, which ->
                        Toast.makeText(activity, "clicked No", Toast.LENGTH_LONG).show()
                    }
                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()


                }
            })

        })


        // projectNameArray.addAll(projectViewModel._projectList)


        /*.observe(viewLifecycleOwner, Observer {


            adapter = ProjectRecyclerAdapter(projectNameArray)
            rv_project.adapter = adapter


            projectNameArray.clone()//.add(it)//.add(it)
            adapter!!.notifyDataSetChanged()
        })*/



        return root
    }

    private fun getDataFromFirestore() {

        //orderBy("date",Query.Direction.DESCENDING).


    }

}
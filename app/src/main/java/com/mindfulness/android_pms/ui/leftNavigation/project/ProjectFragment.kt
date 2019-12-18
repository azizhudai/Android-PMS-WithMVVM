package com.mindfulness.android_pms.ui.leftNavigation.project

import android.os.Bundle
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
            adapter = ProjectRecyclerAdapter(it)
            rv_project.adapter = adapter
            adapter!!.notifyDataSetChanged()
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
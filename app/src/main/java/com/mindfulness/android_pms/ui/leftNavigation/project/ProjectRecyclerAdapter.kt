package com.mindfulness.android_pms.ui.leftNavigation.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindfulness.android_pms.R

class ProjectRecyclerAdapter(//private val userIdArray: ArrayList<String>,
    //private val projectIdArray: ArrayList<String>,
    private val projectName: ArrayList<String>
) : RecyclerView.Adapter<ProjectRecyclerAdapter.PostHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_project_row, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return projectName.size
    }

    override fun onBindViewHolder(holder: ProjectRecyclerAdapter.PostHolder, position: Int) {

        holder.rvProjectName?.text = projectName[position]
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var rvProjectName: TextView? = null

        init {
            rvProjectName = view.findViewById(R.id.rv_project_name_text)
        }

    }

}
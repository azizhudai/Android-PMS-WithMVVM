package com.mindfulness.android_pms.ui.leftNavigation.project

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project

class ProjectRecyclerAdapter(//private val userIdArray: ArrayList<String>,
    //private val projectIdArray: ArrayList<String>,
    private val projectName: ArrayList<Project>,
    private val mCtx: Context
) : RecyclerView.Adapter<ProjectRecyclerAdapter.PostHolder>() {

    var editClickStatus: MutableLiveData<HashMap<String,Any>> = MutableLiveData()
    var deleteClickStatus: MutableLiveData<Boolean> = MutableLiveData()

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

        holder.rvProjectName?.text = projectName.get(position).projectName

        holder.buttonViewOption!!.setOnClickListener(View.OnClickListener {
            //creating a popup menu
            //creating a popup menu
            val popup = PopupMenu(mCtx, holder.buttonViewOption)
            //inflating menu from xml resource
            //inflating menu from xml resource
            popup.inflate(R.menu.rv_options_menu)
            //adding click listener
            //adding click listener
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menu1 -> {
                            editClickStatus.value = hashMapOf("pid" to  projectName.get(position).projectId, "statu" to true)
                        }
                        R.id.menu2 -> {
                            deleteClickStatus.value = true
                        }

                    }
                    return false
                }
            })
            //displaying the popup
            //displaying the popup
            popup.show()

        })
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var rvProjectName: TextView? = null
        var buttonViewOption: TextView? = null

        init {
            rvProjectName = view.findViewById(R.id.rv_project_name_text)
            buttonViewOption = view.findViewById(R.id.textViewOptions)
        }

    }

}
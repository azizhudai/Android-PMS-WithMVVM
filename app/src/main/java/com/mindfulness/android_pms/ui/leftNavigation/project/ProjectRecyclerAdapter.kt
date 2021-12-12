package com.mindfulness.android_pms.ui.leftNavigation.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project


//(//private val userIdArray: ArrayList<String>,
//    //private val projectIdArray: ArrayList<String>,
//    private val projectName: ArrayList<Project>,
//    private val mCtx: Context
//)
class ProjectRecyclerAdapter(
    options: FirestoreRecyclerOptions<Project>,
    //var projectList: MutableList<Project?>,
    private val mCtx: Context
) :
    FirestoreRecyclerAdapter<Project, ProjectRecyclerAdapter.PostHolder>(options) {

    var listener: OnItemClickListener? = null
    var editClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()
    var deleteClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_project_row, parent, false)
        return PostHolder(view)
    }

    /* override fun getItemCount(): Int {
         return projectName.size
     }*/

    /*fun removeItem(position: Int) {
        projectName.removeAt(position)
        notifyDataSetChanged()
    }*/

    /*override fun onBindViewHolder(holder: ProjectRecyclerAdapter.PostHolder, position: Int) {

        // holder.rvProjectName?.text = projectName.get(position).projectName

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
                            editClickStatus.value = hashMapOf(
                                "pid" to projectName.get(position).projectId,
                                "statu" to true
                            )
                        }
                        R.id.menu2 -> {
                            deleteClickStatus.value = hashMapOf(
                                "pid" to projectName.get(position).projectId,
                                "position" to position,
                                "pname" to projectName.get(position).projectName,
                                "statu" to true
                            )
                        }

                    }
                    return false
                }
            })
            //displaying the popup
            //displaying the popup
            popup.show()

        })
    }*/

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var rvProjectName: TextView? = null
        var buttonViewOption: TextView? = null
        var card_viewProjectList:CardView? = null

        init {
            rvProjectName = view.findViewById(R.id.rv_project_name_text)
            buttonViewOption = view.findViewById(R.id.textViewOptions)
            card_viewProjectList = view.findViewById(R.id.card_viewProjectList)
           /* view.setOnClickListener(View.OnClickListener(fun(v: View) {

            }))*/
        }
    }

     interface OnItemClickListener {
         fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
     }

    fun setOnItemClickListener(listener: OnItemClickListener) {

        this.listener = listener
    }

    /*interface OnItemClickListener {
        fun onItemClicked(documentSnapshot: DocumentSnapshot, project: Project)
    }*/


    override fun onBindViewHolder(holder: PostHolder, position: Int, project: Project) {

        holder.rvProjectName!!.text = project.projectName
        //projectList = project

        holder.buttonViewOption!!.setOnClickListener(View.OnClickListener {
            //creating a popup menu
            //creating a popup menu
            val popup = PopupMenu(mCtx, holder.buttonViewOption)
            //inflating menu from xml resource
            //inflating menu from xml resource
            popup.inflate(R.menu.rv_options_menu)
            //adding click listener
            //adding click listener
            popup.setOnMenuItemClickListener { item ->
                when (item.getItemId()) {
                    R.id.menu1 -> {
                        editClickStatus.value = hashMapOf(
                            "pid" to project.projectId,
                            "statu" to true
                        )
                    }
                    R.id.menu2 -> {
                        deleteClickStatus.value = hashMapOf(
                            "pid" to project.projectId,
                            "position" to position,
                            "pname" to project.projectName,
                            "statu" to true
                        )
                    }

                }
                false
            }
            //displaying the popup
            //displaying the popup
            popup.show()

        })

        holder.card_viewProjectList!!.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION && listener != null) {

                listener!!.onItemClick(documentSnapshot = snapshots.getSnapshot(position),position = position)
            }
        }

    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    /* override fun onChildChanged(
         type: ChangeEventType,
         snapshot: DocumentSnapshot,
         newIndex: Int,
         oldIndex: Int
     ) {

         when (type) {
             ChangeEventType.ADDED -> notifyItemInserted(newIndex)
             ChangeEventType.CHANGED -> notifyItemChanged(newIndex)
             ChangeEventType.REMOVED -> notifyItemRemoved(newIndex)
             ChangeEventType.MOVED -> notifyItemMoved(oldIndex, newIndex)
         }
     }*/


}

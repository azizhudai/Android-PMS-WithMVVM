package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment

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
import com.mindfulness.android_pms.data.pojo.Task

class TaskRecyclerAdapter(options: FirestoreRecyclerOptions<Task>,
    //var projectList: MutableList<Project?>,
                          private val mCtx: Context
) :  FirestoreRecyclerAdapter<Task, TaskRecyclerAdapter.PostHolder>(options) {

    var listener: OnItemClickListener? = null
    var editClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()
    var deleteClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_task_row, parent, false)
        return PostHolder(view)
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var rvTaskName: TextView? = null
        var buttonViewOption: TextView? = null
        var card_viewTaskList: CardView? = null

        init {
            rvTaskName = view.findViewById(R.id.rv_task_name_text)
            buttonViewOption = view.findViewById(R.id.textViewOptions)
            card_viewTaskList = view.findViewById(R.id.card_viewTaskList)

        }
    }

    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {

        this.listener = listener
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int, task: Task) {
        holder.rvTaskName!!.text = task.taskTitle
        //projectList = project
        holder.buttonViewOption!!.setOnClickListener(View.OnClickListener {
            //creating a popup menu
            val popup = PopupMenu(mCtx, holder.buttonViewOption)
            //inflating menu from xml resource
            popup.inflate(R.menu.rv_options_menu)
            //adding click listener
            popup.setOnMenuItemClickListener { item ->
                when (item.getItemId()) {
                    R.id.menu1 -> {
                        editClickStatus.value = hashMapOf(
                            "pid" to task.taskId!!,
                            "statu" to true
                        )
                    }
                    R.id.menu2 -> {
                        deleteClickStatus.value = hashMapOf(
                            "pid" to task.taskId!!,
                            "position" to position,
                            "pname" to task.taskTitle,
                            "statu" to true
                        )
                    }
                }
                false
            }
            popup.show()

        })

        holder.card_viewTaskList!!.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION && listener != null) {

                listener!!.onItemClick(documentSnapshot = snapshots.getSnapshot(position),position = position)
            }
        }

    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

}

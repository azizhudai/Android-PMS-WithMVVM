package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.adapter

import android.content.Context
import android.graphics.Color
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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.pojo.TaskDivideCard
import com.mindfulness.android_pms.data.pojo.TaskKanban


//(//private val userIdArray: ArrayList<String>,
//    //private val projectIdArray: ArrayList<String>,
//    private val projectName: ArrayList<Project>,
//    private val mCtx: Context
//)
class DivideInfiniteRecyclerAdapter(
    options: FirestoreRecyclerOptions<TaskDivideCard>,
    //var projectList: MutableList<Project?>,
    private val mCtx: Context
) :
    FirestoreRecyclerAdapter<TaskDivideCard, DivideInfiniteRecyclerAdapter.PostHolder>(options) {

    var listener: OnItemClickListener? = null
    var editClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()
    var deleteClickStatus: MutableLiveData<HashMap<String, Any>> = MutableLiveData()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_infinite_divide_row, parent, false)
        return PostHolder(view)
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var tv_cardTitle: TextView? = null
        var buttonViewOption: TextView? = null
        var cv_infinite: CardView? = null

        init {
            tv_cardTitle = view.findViewById(R.id.tv_cardTitle)
            buttonViewOption = view.findViewById(R.id.textViewOptions)
            cv_infinite = view.findViewById(R.id.cv_infinite)
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


    override fun onBindViewHolder(holder: PostHolder, position: Int, taskCard: TaskDivideCard) {

        holder.tv_cardTitle!!.text = taskCard.CardTitle
        //projectList = project

        holder.cv_infinite!!.setBackgroundColor(Color.RED)
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
                            "pid" to taskCard.projectId,
                            "statu" to true
                        )
                    }
                    R.id.menu2 -> {
                        deleteClickStatus.value = hashMapOf(
                            "cid" to taskCard.cardId,
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

        holder.cv_infinite!!.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION && listener != null) {

                listener!!.onItemClick(
                    documentSnapshot = snapshots.getSnapshot(position),
                    position = position
                )
            }
        }

    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    fun changeStatus(statusId: String, position: Int) {

        //val myId: String
        val taskId = snapshots.getSnapshot(position).id
        val refTask =
            FirebaseFirestore.getInstance().collection("TaskKanban")
        //val refProjectLog = db.collection("ProjectLog")
        var refTaskDoc: DocumentReference

        refTaskDoc = refTask.document(taskId)
        //myId = taskId

       /* var task2 = TaskKanban(
            task.,
            ,
            statusId,
            ,
        ,
        ,

        )*/

        /*var firebaseAuth = FirebaseAuth.getInstance()*/
        /*var uid = firebaseAuth.uid
        var project__ = Project(
            myId,
            project.projectName,
            project.projectDetail,
            project.projectStartDate,
            project.projectEndDate,
            project.projectCreateDate,
            uid!!,
            project.techId
        )*/

        refTaskDoc.update("taskStatus",statusId)

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
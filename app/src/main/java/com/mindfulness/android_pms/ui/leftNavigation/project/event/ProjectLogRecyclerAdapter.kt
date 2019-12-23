package com.mindfulness.android_pms.ui.leftNavigation.project.event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.ProjectLog
import java.text.SimpleDateFormat
import java.util.*

class ProjectLogRecyclerAdapter(
    options: FirestoreRecyclerOptions<ProjectLog>
) : FirestoreRecyclerAdapter<ProjectLog, ProjectLogRecyclerAdapter.PostHolder>(options) {

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        //View Holder class
        var tvProjectLogDetail: TextView? = null
        var tvProjectLogCreateDate: TextView? = null

        init {

            tvProjectLogDetail = view.findViewById(R.id.tv_projectLogDetail)
            tvProjectLogCreateDate = view.findViewById(R.id.tv_projectLogCreateDate)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_project_log_row, parent, false)
        return PostHolder(
            view
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PostHolder, position: Int, projectLog: ProjectLog) {

        //dil seçeneğine göre değişecek
        var typeName = ""
        // mention the format you need
        //us
        //val myFormat = "MM/dd//yyyy"

        //val date = sdf.format(projectLog.createDate.toDate())
        //date.toString()
        when (projectLog.projectStatu) {
            0 -> {
                holder.tvProjectLogDetail!!.text = ""
                holder.tvProjectLogCreateDate!!.text = ""
            }
            1 -> {
                typeName = "tarafından proje adı oluşturuldu."
            }
            2 -> {
                typeName = "tarafından proje adı düzenlendi."
            }
            3 -> {
                typeName = "tarafından proje detay düzenlendi."
            }
            4 -> {
                typeName = "tarafından proje başlangıç tarihi düzenlendi."
            }
            5 -> {
                typeName = "tarafından proje bitiş tarihi düzenlendi."
            }
        }
        holder.tvProjectLogDetail!!.text = "${projectLog.projectLogDetail} \n$typeName"  //${projectLog.createUserEmail}

        val myFormat = "dd/MM/yyyy HH:mm" // mention the format you need
        //us
        //val myFormat = "MM/dd//yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val date = sdf.format(projectLog.createDate.toDate())
        holder.tvProjectLogCreateDate!!.text = date.toString()  //projectLog.createDate.toDate().toString()//
    }

}
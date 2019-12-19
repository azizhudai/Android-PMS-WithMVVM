package com.mindfulness.android_pms.ui.leftNavigation.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindfulness.android_pms.data.firebase.FirebaseSource
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.repositories.ProjectRepository
import java.util.ArrayList

class ProjectViewModel(
    // private var repository: ProjectRepository = ProjectRepository(FirebaseSource())
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    /*val user by lazy {
        repository.currentUser()
    }*/


    val _projectList = MutableLiveData<ArrayList<Project>>().apply {


        var projectStr: ArrayList<Project> = ArrayList()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var project: Project
        projectStr.clear()

        db.collection("Project").orderBy(
            "projectCreateDate",
            Query.Direction.DESCENDING
        )//.whereEqualTo("createUserId", firebaseAuth.uid)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                    // projectStr.add("Error")
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {
                            val documents = snapshot.documents

                            projectStr.clear()
                            var i = 0
                            for (document in documents) {
                                //  projectStr.add(1,pro)
                                project = Project(
                                    document.get("projectId") as String,
                                    document.get("projectName") as String,
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                )
                                projectStr.add(i, project)
                                i++
                                //   projectStr.add(document.get("projectId") as String)
                                // projectStr.add(document.get("projectName") as String)

                            }
                            value = projectStr
                        }
                    }
                }
            }
        // Thread.sleep(3000)
        //  projectStr.add("aaaaaaa")
        //projectStr[1] = "aaaaaaa"

        //Thread.sleep(3000)
        //repository.userProjectList("qqq")
    }
    val projectList: LiveData<ArrayList<Project>> = _projectList
    //_projectList = repository.userProjectList("qqq")/*.apply {
    /*_projectList = repository.userProjectList("qqq")
}*/
    //val projectList: LiveData<Any> = _projectList
}
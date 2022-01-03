package com.mindfulness.android_pms.data.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindfulness.android_pms.data.pojo.Project
import com.mindfulness.android_pms.data.pojo.TaskKanban
import io.reactivex.Completable

class ProjectSource {

    var projectStr: ArrayList<String> = ArrayList()

    var firebaseSource = FirebaseSource();
    var firebaseAuth =  firebaseSource.firebaseAuth
    var db = firebaseSource.db;

    fun projectInsert(project: Project) = Completable.create { emitter ->

        val myId: String
        val refProject =
            db.collection("Project")
        //val refProjectLog = db.collection("ProjectLog")
        var refProjectDoc: DocumentReference
        if (project.projectId.isNullOrEmpty()) {
            refProjectDoc = refProject.document()
            myId = refProjectDoc.id
        } else {
            refProjectDoc = refProject.document(project.projectId)
            myId = project.projectId
        }

        /*var firebaseAuth = FirebaseAuth.getInstance()*/
        val uid = firebaseAuth.uid
        val project__ = Project(
            myId,
            project.projectName,
            project.projectDetail,
            project.projectStartDate,
            project.projectEndDate,
            project.projectCreateDate,
            uid!!,
            project.techId
        )

        refProjectDoc.set(project__).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful)

                emitter.onComplete()
        }.addOnFailureListener { exception ->
            emitter.onError(exception)
        }
    }

    fun addKanbanTask(task: TaskKanban) = Completable.create { emitter ->
        val myId: String
        val refTaskKanban =
            db.collection("TaskKanban")

        val refDoc: DocumentReference
        if (task.taskId.isEmpty()) {
            refDoc = refTaskKanban.document()
            myId = refDoc.id
        } else {
            refDoc = refTaskKanban.document(task.taskId)
            myId = task.taskId
        }

        val uid = firebaseAuth.uid
        val task_ = TaskKanban(
            myId,
            task.projectId,
            task.taskTitle,
            task.taskDetail,
            task.taskStarDate,
            task.taskEndDate,
            task.taskCreateDate,
            task.taskCreateUserId,
            task.taskStatus
        )

        refDoc.set(task_).addOnCompleteListener { task ->
            if (task.isCanceled && task.isSuccessful)
                emitter.onComplete()
        }.addOnFailureListener { ex ->
            emitter.onError(ex)
        }
    }

    fun userProjectList(userId: String): java.util.ArrayList<String> {

        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("Project")//.whereEqualTo("createUserId", firebaseAuth.uid)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    projectStr.add("Error")
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {
                            val documents = snapshot.documents
                            var i = 0
                            for (document in documents) {
                                // val comment = document.get("comment") as String
                                projectStr.add(document.get("projectName") as String)
                                // val downloadUrl = document.get("downloadUrl") as String
                                //val timestamp = document.get("date") as Timestamp
                                //val date = timestamp.toDate()
                                i++
                            }
                        }
                    }
                }
            }
        return projectStr
    }

    fun getProjectLogList(projectId: String): Query {

        var projectLogDoc = db.collection("ProjectLog")
        var query = projectLogDoc.orderBy(
            "createDate",
            Query.Direction.DESCENDING
        ).whereEqualTo("projectId", projectId) //.whereEqualTo("createUserId", firebaseAuth.uid)

        /*  var options =
              FirestoreRecyclerOptions.Builder<ProjectLog>().setQuery(query, ProjectLog::class.java).build()
  */
        return query
    }
}
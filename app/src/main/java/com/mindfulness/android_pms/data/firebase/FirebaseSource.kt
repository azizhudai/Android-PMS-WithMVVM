package com.mindfulness.android_pms.data.firebase

import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mindfulness.android_pms.data.pojo.Project
import io.reactivex.Completable
import com.mindfulness.android_pms.data.pojo.User

class FirebaseSource {
    var projectStr: ArrayList<String> = ArrayList()
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    /*val user = hashMapOf<String, Any>(
                        "email" to email,
                        "createdTime" to Timestamp.now()
                    )*/
                    /* val user = User( email, Timestamp.now())

                     db.collection("Users")
                         .add(user).addOnCompleteListener { task ->
                             if (task.isComplete && task.isSuccessful)

                         }.addOnFailureListener { exception -> emitter.onError(exception) }*/

                    emitter.onComplete()
                } else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /*
    * ref.set(project).addOnCompleteListener { task ->
                if (task.isComplete && task.isSuccessful)

                   Toast.makeText(context,"Başarılı",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {exception ->  Toast.makeText(context,exception.toString(),Toast.LENGTH_SHORT).show() }
    * */
    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) = Completable.create { emitter ->
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                emitter.onComplete()
                /*Toast.makeText(
                    this,
                    "Google Sihn in Success ${it.result.toString()}",
                    Toast.LENGTH_LONG
                ).show()*/
                // startActivity(HomeActivity.getLaunchIntent(this))
            } else {
                emitter.onError(it.exception!!)
                //Toast.makeText(this, "Google sign in failed:(...", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun projectInsert(project: Project) = Completable.create { emitter ->

        val myId: String
        val ref =
            db.collection("Project")
        var refDoc: DocumentReference
        if (project.projectId.isNullOrEmpty()) {
            refDoc = ref.document()
            myId = refDoc.id
        } else {
            refDoc = ref.document(project.projectId)
            myId = project.projectId
        }

        /*var firebaseAuth = FirebaseAuth.getInstance()*/
        var uid = firebaseAuth.uid
        var project__ = Project(
            myId,
            project.projectName,
            project.projectDetail,
            project.projectStartDate,
            project.projectEndDate,
            project.projectCreateDate,
            uid!!
        )

        refDoc.set(project__).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful)

                emitter.onComplete()
        }.addOnFailureListener { exception ->
            emitter.onError(exception)
        }
    }

    fun userProjectList(userId: String): ArrayList<String> {

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

}

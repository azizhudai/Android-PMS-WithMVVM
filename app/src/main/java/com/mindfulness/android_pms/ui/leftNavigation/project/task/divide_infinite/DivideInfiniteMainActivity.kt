package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.TaskDivideCard
import com.mindfulness.android_pms.databinding.ActivityHomeBinding
import com.mindfulness.android_pms.databinding.ContentDivideInfiniteBinding
import com.mindfulness.android_pms.ui.auth.AuthListener
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment.TaskListFragment
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.adapter.DivideInfiniteRecyclerAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

@Suppress("DEPRECATED_IDENTITY_EQUALS", "UNUSED_VALUE", "DEPRECATION")
class DivideInfiniteMainActivity : AppCompatActivity(), AuthListener, KodeinAware,
    AdapterView.OnItemSelectedListener {

    override val kodein by kodein()
    private val factory:DivideInfiniteViewModelFactory by instance()
    private lateinit var viewModel:DivideInfiniteViewModel
    private var adapter:DivideInfiniteRecyclerAdapter? = null

    var rv_divide_invite: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_divide_infinite) //activity_divide_infinite_main

        val binding: ContentDivideInfiniteBinding =
            DataBindingUtil.setContentView(this, R.layout.content_divide_infinite)
        viewModel = ViewModelProviders.of(this, factory).get(DivideInfiniteViewModel::class.java)
        binding.viewmodel = viewModel
        //viewModelDivideInf.aou

        rv_divide_invite = findViewById(R.id.rv_divide_invite)
        getDataDBTaskDivide(rv_divide_invite!!,"")

      /*  val horizontalScrollView1 = findViewById<LinearLayout>(R.id.shapeLayout)

        val carView = CardView(this)

        //carView.layout_width

        carView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
*/

    }
    override fun onStart() {
        super.onStart()
        if (adapter != null)
            adapter!!.startListening()
    }
    override fun onStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
       /* super.onStart()
        if (adapter != null)
            adapter!!.startListening()*/
    }
    override fun onResume() {
        super.onResume()
        if (adapter != null)
            adapter!!.startListening()
    }
    override fun onStop() {
        super.onStop()
        if (adapter != null)
            adapter!!.stopListening()
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getDataDBTaskDivide(rv_divide_invite:RecyclerView,projectId:String){

        //var query = viewModelDivideInf.taskDivideCard
        var db = FirebaseFirestore.getInstance()
        var projectLogDoc = db.collection("TaskDivideCard")
        var query = projectLogDoc/*.whereEqualTo(
            "projectId",
            projectId
        )*/
        var aa = viewModel.taskDivideCard.value
        var options = FirestoreRecyclerOptions.Builder<TaskDivideCard>().setQuery(aa!!, TaskDivideCard::class.java)
            .build()
        adapter = DivideInfiniteRecyclerAdapter(options = options,mCtx = this@DivideInfiniteMainActivity.applicationContext)
        rv_divide_invite.setHasFixedSize(true)

        var layoutManager = LinearLayoutManager(this@DivideInfiniteMainActivity.applicationContext,LinearLayoutManager.HORIZONTAL,false)
        rv_divide_invite.layoutManager = layoutManager
        rv_divide_invite.adapter = adapter

        adapter!!.setOnItemClickListener(object :DivideInfiniteRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
               // TODO("Not yet implemented")
                val id = documentSnapshot.id
                Toast.makeText(
                    this@DivideInfiniteMainActivity.applicationContext,
                    "id: $id",
                    Toast.LENGTH_LONG
                ).show()

                changeFragment(TaskListFragment(id))
            }

        })
    }
    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}

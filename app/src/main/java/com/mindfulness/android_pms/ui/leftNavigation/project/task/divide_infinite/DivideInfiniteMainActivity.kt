package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.data.pojo.TaskDivideCard
import com.mindfulness.android_pms.databinding.ContentDivideInfiniteBinding
import com.mindfulness.android_pms.ui.auth.AuthListener
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment.TaskListFragment
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.add.AddCardDivideInfiniteActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

@Suppress("DEPRECATED_IDENTITY_EQUALS", "UNUSED_VALUE", "DEPRECATION")
class DivideInfiniteMainActivity : AppCompatActivity(), AuthListener, KodeinAware,
    AdapterView.OnItemSelectedListener {

    override val kodein by kodein()
    private val factory:DivideInfiniteViewModelFactory by instance()
    private lateinit var viewModel:DivideInfiniteViewModel
    private var adapter: DivideInfiniteRecyclerAdapter? = null

    var rv_divide_invite: RecyclerView? = null

    var pid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_divide_infinite) //activity_divide_infinite_main

        val binding: ContentDivideInfiniteBinding =
            DataBindingUtil.setContentView(this, R.layout.content_divide_infinite)
        viewModel = ViewModelProviders.of(this, factory).get(DivideInfiniteViewModel::class.java)
        binding.viewmodel = viewModel
        //viewModelDivideInf.aou

        rv_divide_invite = findViewById(R.id.rv_divide_invite)

        val actionbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(actionbar)

        val intent1: Intent = intent
        pid = intent1.getStringExtra("pid")
        pid?.let {
            val pName = intent1.getStringExtra("pname")
          //  val pageTitle = findViewById<TextView>(R.id.tv_page_title)
           // pageTitle.text = pName
            title = pName
          //  actionbar!!.title = pName
            // Creating the new Fragment with the name passed in.
            //val fragment = DoingFragment.newInstance(pid!!)
            getDataDBTaskDivide(rv_divide_invite!!, pid!!)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        adapter!!.editClickStatus.observe(this, Observer {
            if (it.get("statu") == true) {
                Intent(this, AddCardDivideInfiniteActivity::class.java).also { itt ->
                    //MainMenuActivity
                    //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    itt.putExtra("cid", it.get("cid") as String)
                    itt.putExtra("pid", it.get("pid") as String)
                    startActivity(itt)
                }
            }
        })

        adapter!!.deleteClickStatus.observe(this, Observer {
            if (it.get("statu") == true) {

                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                //set message for alert dialog
                var message = it.get("cname") as String
                builder.setMessage(message)
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    //Toast.makeText(activity, "clicked yes", Toast.LENGTH_LONG).show()

                    var position = it.get("position") as Int
                    adapter!!.deleteItem(position)

                }
                //performing cancel action
                builder.setNeutralButton("Cancel") { _, _ ->

                }
                //performing negative action
                builder.setNegativeButton("No") { _, _ ->
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        })



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
      //  var db = FirebaseFirestore.getInstance()
    //    var projectLogDoc = db.collection("TaskDivideCard")
       /* var query = projectLogDoc.whereEqualTo(
            "projectId",
            projectId
        )*/
        val aa = viewModel.taskCardList(projectId).value //viewModel.taskDivideCard.value
        val options = FirestoreRecyclerOptions.Builder<TaskDivideCard>().setQuery(aa!!, TaskDivideCard::class.java)
            .build()
        adapter = DivideInfiniteRecyclerAdapter(options = options,mCtx = this@DivideInfiniteMainActivity.applicationContext)
        rv_divide_invite.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this@DivideInfiniteMainActivity.applicationContext,LinearLayoutManager.HORIZONTAL,false)
        rv_divide_invite.layoutManager = layoutManager
        rv_divide_invite.adapter = adapter

        adapter!!.setOnItemClickListener(object : DivideInfiniteRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
               // TODO("Not yet implemented")
                val id = documentSnapshot.id
                /*Toast.makeText(
                    this@DivideInfiniteMainActivity.applicationContext,
                    "id: $id",
                    Toast.LENGTH_LONG
                ).show()*/

                changeFragment(TaskListFragment(id))
            }

        })
    }
    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.add_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {

            Intent(this@DivideInfiniteMainActivity, AddCardDivideInfiniteActivity::class.java).also { itt ->
                //MainMenuActivity
                //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                itt.putExtra("pid", pid as String)
                startActivity(itt)
            }

            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.action_two) {
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.action_three) {
            Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }


}

package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.ui.leftNavigation.project.task.kanban.TaskManagementActivity

class TaskKanbanAddActivity : AppCompatActivity() {

    var pid:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_kanban_add)

        val actionbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(actionbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        val intent1: Intent = intent
        pid = intent1.getStringExtra("pid")
        pid?.let {
            val pName = intent1.getStringExtra("pname")
            //  val pageTitle = findViewById<TextView>(R.id.tv_page_title)
            // pageTitle.text = pName
            title = pName

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_button,menu)
        return true

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.action_one) {

            Intent(this@TaskKanbanAddActivity, TaskManagementActivity::class.java).also { itt ->
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

package com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.add

//import android.annotation.SuppressLint
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.databinding.ActivityAddCardDivideInfiniteBinding
import com.mindfulness.android_pms.ui.auth.AuthListener
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.DivideInfiniteViewModel
import com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.DivideInfiniteViewModelFactory
import kotlinx.android.synthetic.main.activity_add_card_divide_infinite.*
import kotlinx.android.synthetic.main.popup_colors.*
import kotlinx.android.synthetic.main.popup_colors.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AddCardDivideInfiniteActivity : AppCompatActivity(), KodeinAware, AuthListener {

    override val kodein by kodein()
    private val factory: DivideInfiniteViewModelFactory by instance()

    private lateinit var viewModel: DivideInfiniteViewModel
    var pid: String? = null
    var cid: String? = null
    // var et_cardTitle:EditText? = null
    //  var et_cardDetal: EditText? = null
  //  var grid_view:GridView? = null
   // var text_view:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add_card_divide_infinite)

        val binding: ActivityAddCardDivideInfiniteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_card_divide_infinite)
        viewModel = ViewModelProviders.of(this, factory).get(DivideInfiniteViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        //actionbar
        val actionbar = findViewById<Toolbar>(R.id.toolbar2) //supportActionBar
        setSupportActionBar(actionbar)
        //set actionbar title
        title = "New Card"
        //actionbar!!.title = "New Activity"
        //set back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // et_cardTitle = findViewById(R.id.et_cardTitle)
        // et_cardDetal = findViewById(R.id.et_cardDetal)

       //  grid_view = findViewById<GridView>(R.id.grid_view)
      //   text_view = findViewById<TextView>(R.id.text_view)
        val intent1: Intent = intent
        cid = intent1.getStringExtra("cid")
       // pid = intent1.getStringExtra("pid")

        if(!cid.isNullOrEmpty()){
            viewModel.getExistCard(cid!!).observe(this, Observer {card->
                if(card != null){
                    title = card.cardTitle
                    et_cardTitle.setText(card.cardTitle)
                    et_cardDetal.setText(card.cardDetail)
                }
            })
        }

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

            //  et_cardTitle = findViewById(R.id.et_cardTitle)
            // et_cardDetal = findViewById(R.id.et_cardDetal)
            /*   var cardClass = TaskDivideCard(
                   "0",
                   "0",
                   "111",
                   et_cardTitle.toString(),
                   et_cardTitle.toString(),
                   0
               )*/
            val intent1: Intent = intent
            cid = intent1.getStringExtra("cid")
            pid = intent1.getStringExtra("pid")
            if (pid == null) {
                println("pid is empty." + pid)
                Toast.makeText(this, "Project not found! Try again pls", Toast.LENGTH_LONG).show()
            } else {
                try {
                    viewModel.insertCardClick(cid, pid!!)
                } catch (ex: Exception) {
                    Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
                }

            }

           // Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
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

    override fun onStarted() {
        Toast.makeText(
            this@AddCardDivideInfiniteActivity,
            "Kayıt İşlemi Başladı.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSuccess() {
        Toast.makeText(this@AddCardDivideInfiniteActivity, "Başarılı!", Toast.LENGTH_LONG).show()
        //  startMainMenuActivity()
        finish()
        //super.onBackPressed();
    }

    override fun onFailure(message: String) {
        Toast.makeText(this@AddCardDivideInfiniteActivity, message, Toast.LENGTH_LONG).show()
    }

   // @SuppressLint("WrongViewCast")
    fun chooseCardColor(view: View) {


        // Initialize a list of string values
        val colors = listOf<String>("Red","Green","Yellow","Blue","Orange","Pink","White","Gray")

       val colorsCode = listOf<String>("#5c2b29","#345920","#F4D03F","#5499C7","#EB984E","#F1948A","#FDFEFE","#E5E8E8")

       // Initialize a new array adapter instance
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_list_item_1, // Layout
            colors // List
        )

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        // builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
        //   .setNegativeButton("No", dialogClickListener).show()

        val inflater = layoutInflater
        builder.setTitle("Choose Card Color")
        val dialogLayout = inflater.inflate(R.layout.popup_colors, null)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Toast.makeText(
                applicationContext,
                "EditText is " + "tılşandı",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()

       // Set the grid view adapter/data source
      // grid_view.let {
       dialogLayout.grid_view.adapter = adapter

           // Set an item click listener for grid view items
       dialogLayout.grid_view.onItemClickListener = object : AdapterView.OnItemClickListener {
               @SuppressLint("ResourceAsColor")
               override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                   // Get the GridView selected/clicked item text
                   val selectedItem = parent.getItemAtPosition(position).toString()

                   // Display the selected/clicked item text and position on TextView
                   dialogLayout.text_view!!.text =
                       "GridView item clicked : $selectedItem \nAt index position : $position"
                   dialogLayout.text_view.setBackgroundColor(Color.parseColor(colorsCode.get(position)))

               }
           }

           //  }
      // }


   }

}

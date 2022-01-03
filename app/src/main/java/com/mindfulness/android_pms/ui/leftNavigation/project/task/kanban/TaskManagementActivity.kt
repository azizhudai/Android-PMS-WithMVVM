package com.mindfulness.android_pms.ui.leftNavigation.project.task.kanban

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mindfulness.android_pms.R
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.doing.DoingFragment
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.done.DoneFragment
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.fragments.todo.TodoFragment
import com.mindfulness.android_pms.utils.startKanbanTaskAddActivity

class TaskManagementActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    var pid: String? = null
    var pName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_management)

        //get intent values
        val intent1: Intent = intent
        pid = intent1.getStringExtra("pid")
        pid?.let {
            pName = intent1.getStringExtra("pname")
            var pageTitle = findViewById<TextView>(R.id.tv_page_title)
            pageTitle.text = pName
            // Creating the new Fragment with the name passed in.
            //val fragment = DoingFragment.newInstance(pid!!)

        }


        /*val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        */

    }

    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabs)
    }

    private fun setStatePageAdapter() {
        val myViewPageStateAdapter =
            MyViewPageStateAdapter(
                supportFragmentManager
            )
        //  val tab1 = R.string.tab_text_1
        myViewPageStateAdapter.addFragment(TodoFragment(), "To Do")
        myViewPageStateAdapter.addFragment(DoingFragment(), "Doing")
        myViewPageStateAdapter.addFragment(DoneFragment(), "Done")
        viewPager.adapter = myViewPageStateAdapter

        tabLayout.setupWithViewPager(viewPager, true)
        tabLayout.setBackgroundColor(Color.GRAY)

        // Disable clip to padding
        viewPager.clipToPadding = false
        // set padding manually, the more you set the padding the more you see of prev & next page
        viewPager.setPadding(40, 0, 40, 0)
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        viewPager.pageMargin = 20
    }

    @Suppress("DEPRECATION")
    inner class MyViewPageStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val fragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            var bundle = Bundle()
            bundle.putString("pid", pid)
            return when (position) {
                0 -> {
                    var frag = TodoFragment()
                    frag.arguments = bundle
                    frag
                }
                1 -> {
                    var frag = DoingFragment()
                    frag.arguments = bundle
                    frag
                }
                2 -> {
                    var frag = DoneFragment()
                    frag.arguments = bundle
                    frag
                }
                else -> {
                    var frag = TodoFragment()
                    frag.arguments = bundle
                    frag
                }
            }
            //return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)

        }
    }

    override fun onStart() {
        super.onStart()
        initViews()
        setStatePageAdapter()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // setAdapter();


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                //   viewPager.notifyAll();
            }
        })

    }

    fun addKanbanTask(view: View) {
        startKanbanTaskAddActivity(pid, pName)
    }
}
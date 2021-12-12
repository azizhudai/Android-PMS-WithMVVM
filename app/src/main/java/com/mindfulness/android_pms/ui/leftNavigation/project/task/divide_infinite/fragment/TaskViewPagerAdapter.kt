package com.mindfulness.android_pms.ui.leftNavigation.project.task.divide_infinite.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.mindfulness.android_pms.R

class TaskViewPagerAdapter (
    var mContext: Context,
    //var mListScreen: List<Task>
    var mListFragment:List<TaskListFragment>
) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutTask = inflater.inflate(R.layout.task_list_fragment, null)
        val rvTaskList = layoutTask.findViewById<RecyclerView>(R.id.rv_task_list)


        /*val imgSlide =
            layoutTask.findViewById<ImageView>(R.id.intro_img)
        val title = layoutScreen.findViewById<TextView>(R.id.intro_title)
        val description = layoutScreen.findViewById<TextView>(R.id.intro_description)
        title.text = mListScreen[position].title
        description.text = mListScreen[position].description
        imgSlide.setImageResource(mListScreen[position].screenImg)*/
        container.addView(layoutTask)
        return layoutTask
    }

    override fun getCount(): Int {
        return mListFragment.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {

        var index = mListFragment.indexOf(`object`)
        return if(index == -1)
            PagerAdapter.POSITION_NONE
        else
            index
    }

}
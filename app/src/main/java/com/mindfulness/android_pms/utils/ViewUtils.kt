package com.mindfulness.android_pms.utils

import android.content.Context
import android.content.Intent
import com.mindfulness.android_pms.ui.main.MainActivity
import com.mindfulness.android_pms.ui.auth.LoginActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.event.ProjectAddActivity
import com.mindfulness.android_pms.ui.leftNavigation.project.task.ui.kanban.add.TaskKanbanAddActivity

fun Context.startHomeActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startMainMenuActivity() =
    Intent(this, MainActivity::class.java).also {//MainMenuActivity
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startProjectAddActivity() =
    Intent(this, ProjectAddActivity::class.java).also {//MainMenuActivity
        //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startKanbanTaskAddActivity(pid: String?, pname: String?) =
    Intent(this, TaskKanbanAddActivity::class.java).also {//MainMenuActivity
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        it.putExtra("pid", pid)
        it.putExtra("pname", pname)
        startActivity(it)
    }
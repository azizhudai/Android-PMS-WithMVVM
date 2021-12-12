package com.mindfulness.android_pms.data.pojo

data class Task(
    var taskId:String?,
    var userId:String,
    var carId:String,
    var taskTitle:String,
    var taskDetail:String?
) {
    constructor():this("","","","","")
}
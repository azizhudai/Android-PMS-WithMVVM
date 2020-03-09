package com.mindfulness.android_pms.data.pojo

data class TaskDivideCard(
    var cardId:String,
    var userId:String,
    var projectId:String,
    var CardTitle:String,
    var taskCound:Int?
) {
    constructor():this("","","","",0)
}
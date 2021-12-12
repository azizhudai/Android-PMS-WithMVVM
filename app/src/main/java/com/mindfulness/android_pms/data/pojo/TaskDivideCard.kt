package com.mindfulness.android_pms.data.pojo

data class TaskDivideCard(
    var cardId:String?,
    var userId:String,
    var projectId:String,
    var cardTitle:String,
    var cardDetail:String?,
    var taskCound:Long?
) {
    constructor():this("","","","","",0)
}
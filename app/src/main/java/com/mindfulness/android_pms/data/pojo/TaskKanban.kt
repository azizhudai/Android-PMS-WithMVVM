package com.mindfulness.android_pms.data.pojo

data class TaskKanban(
    var taskId: String,
    var projectId: String,
    var taskTitle: String,
    var taskDetail: String?,
    var taskStarDate: String?,
    var taskEndDate: String?,
    var taskCreateDate: String,
    var taskCreateUserId:String,
    var taskStatus: String // 1 ise yapılacak 2: ise yapılıyor 3: ise yapıldı
) {
    constructor() : this("", "", "", "", "", "", "","", "")

    constructor(taskStatus: String):this()
}
package com.mindfulness.android_pms.data.pojo

import com.google.firebase.Timestamp

data class Project(
    var projectId: String,
    var projectName: String,
    var projectDetail: String?,
    var projectStartDate: String?,
    var projectEndDate: String?,
    var projectCreateDate: String,
    var createUserId: String
) {

    /*
    private var projectId:String
        get() = field
        private set
    private var projectName:String
        get() = field
        private set
    private var projectDetail:String?
        get() = field
        private set
    private var projectStartDate:Timestamp
        get() = field
        private set
    private var projectEndDate:Timestamp
        get() = field
        private set
    private var projectCreateDate:Timestamp
        get() = field
        private set

    constructor(
        projectId: String,
        projectName: String,
        projectDetail: String?,
        projectStartDate: Timestamp,
        projectEndDate: Timestamp,
        projectCreateDate: Timestamp
    ) {
        this.projectId = projectId
        this.projectName = projectName
        this.projectDetail = projectDetail
        this.projectStartDate = projectStartDate
        this.projectEndDate = projectEndDate
        this.projectCreateDate = projectCreateDate
    }

*/

}
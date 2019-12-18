package com.mindfulness.android_pms.data.pojo

import com.google.firebase.Timestamp

data class User_Project(var userProjectId:String,
                        var userId:String,
                        var projectId:String,
                        var createUserProjectDate:Timestamp) {
}
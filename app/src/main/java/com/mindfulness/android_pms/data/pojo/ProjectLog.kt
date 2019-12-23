package com.mindfulness.android_pms.data.pojo

import com.google.firebase.Timestamp

data class ProjectLog(
    var projectLogId: String,
    var projectStatu: Int,
    var projectId: String,
    var projectLogDetail: String,
    var createDate: Timestamp,
    var createUserEmail: String
) {
    constructor() : this("", 0, "", "", Timestamp.now(), "")

    // 1: projectStatu 1 ise proje oluşturuldu
    // 2: proje adı değişti
    // 3: proje detay değişti
    // 4: proje başlama tarih değişti
    // 5: proje bitiş tarihi değişti

    // duruma göre listede resimler çıkacktır...
}
package com.injent.dnevnik.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Student(
    val id: Long,
    @SerializedName("id_str") val idStr: String,
    @SerializedName("userId") val userID: Long,
    @SerializedName("userId_str") val userIDStr: String,
    val shortName: String,
    val sex: String,
    var averageMark: Float = 0f,
    @SerializedName("birthday") val birthday: Date = Date()
)
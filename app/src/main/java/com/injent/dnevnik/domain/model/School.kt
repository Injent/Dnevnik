package com.injent.dnevnik.domain.model

import com.google.gson.annotations.SerializedName

data class School (
    @SerializedName("id"       ) var id       : Long            = 0,
    @SerializedName("name"     ) var name     : String         = "null",
    @SerializedName("type"     ) var type     : String         = "null",
    @SerializedName("groupIds" ) var groupIds : ArrayList<Long> = arrayListOf()
)
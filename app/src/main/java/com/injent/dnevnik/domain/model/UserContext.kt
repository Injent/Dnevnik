package com.injent.dnevnik.domain.model

import com.google.gson.annotations.SerializedName

data class UserContext(
    @SerializedName("userId"     ) var userId     : Long                = 0,
    @SerializedName("avatarUrl"  ) var avatarUrl  : String              = "",
    @SerializedName("roles"      ) var roles      : ArrayList<String>   = arrayListOf(),
    @SerializedName("schools"    ) var schools    : ArrayList<School>   = arrayListOf(),
    @SerializedName("personId"   ) var personId   : Long                = 0,
    @SerializedName("firstName"  ) var firstName  : String              = "null",
    @SerializedName("lastName"   ) var lastName   : String              = "null",
    @SerializedName("middleName" ) var middleName : String              = "null",
    @SerializedName("shortName"  ) var shortName  : String              = "null",
    @SerializedName("schoolIds"  ) var schoolIds  : ArrayList<Long>      = arrayListOf(),
    @SerializedName("groupIds"   ) var groupIds   : ArrayList<Long>      = arrayListOf()
)
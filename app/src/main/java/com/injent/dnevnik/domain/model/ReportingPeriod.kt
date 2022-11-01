package com.injent.dnevnik.domain.model

import com.google.gson.annotations.SerializedName

data class ReportingPeriod(
    @SerializedName("id_str") val idStr: String,
    @SerializedName("name") val name: String = "",
    @SerializedName("year") val year: Int = 0
)

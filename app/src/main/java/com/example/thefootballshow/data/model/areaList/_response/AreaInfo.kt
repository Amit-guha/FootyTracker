package com.example.thefootballshow.data.model.areaList._response

import com.google.gson.annotations.SerializedName

data class AreaInfo(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("countryCode")
    val countryCode: String? = null,

    @SerializedName("flag")
    val flagUrl: String? = null,

    @SerializedName("parentArea")
    val parentArea: String? = null,

    @SerializedName("parentAreaId")
    val parentAreaId: String? = null
)
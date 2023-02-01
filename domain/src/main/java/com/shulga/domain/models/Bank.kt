package com.shulga.domain.models

import com.google.gson.annotations.SerializedName

class Bank(
    @SerializedName("name") val name: String? = "",
    @SerializedName("url") val url: String? = "",
    @SerializedName("phone")val phone: String= "",
    @SerializedName("city") val city: String= ""
)
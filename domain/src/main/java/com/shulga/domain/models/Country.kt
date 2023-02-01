package com.shulga.domain.models

import com.google.gson.annotations.SerializedName

class Country(
    @SerializedName("numeric") val numeric: Int? = 0,
    @SerializedName("alpha2")val alpha2: String? = "",
    @SerializedName("name")val name: String? = "",
    @SerializedName("emoji")val emoji: String? = "",
    @SerializedName("currency")val currency: String? = "",
    @SerializedName("latitude")val latitude: Double? = 0.0,
    @SerializedName("longitude")val longitude: Double? = 0.0
)
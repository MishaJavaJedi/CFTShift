package com.shulga.domain.models

import com.google.gson.annotations.SerializedName

data class BinModel(
    @SerializedName("number")val number: Number?,
    @SerializedName("scheme") val scheme:String? = "",
    @SerializedName("type") val type:String? ="",
    @SerializedName("brand") val brand: String? ="",
    @SerializedName("prepaid") val prepaid:Boolean?,
    @SerializedName("country") val country: Country? = Country(),
    @SerializedName("bank")val bank: Bank? = Bank()
)







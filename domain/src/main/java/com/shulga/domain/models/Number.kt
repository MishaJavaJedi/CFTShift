package com.shulga.domain.models

import com.google.gson.annotations.SerializedName

class Number(
   @SerializedName("length") val lenght: Int?,
   @SerializedName("luhn") val luhn: Boolean?,

)
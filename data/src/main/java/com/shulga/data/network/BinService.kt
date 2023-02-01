package com.shulga.data.network

import com.shulga.domain.models.BinModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BinService {
    @GET("{cardNumber}")
    fun getCardInfoByBin(@Path("cardNumber") cardNumber: String): Call<BinModel>
}

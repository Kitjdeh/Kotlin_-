package com.example.coinmonitoring.repository

import com.example.coinmonitoring.network.Api
import com.example.coinmonitoring.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()

    suspend fun getInterestCoinPriceData(coin: String) = client.getRecentCoinPrice(coin)

}
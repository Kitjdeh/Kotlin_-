package com.example.coinmonitoring.network.model

import com.example.coinmonitoring.dataModel.RecentPriceData

data class RecentCoinPriceList(

    val status: String,
    val data: List<RecentPriceData>

)



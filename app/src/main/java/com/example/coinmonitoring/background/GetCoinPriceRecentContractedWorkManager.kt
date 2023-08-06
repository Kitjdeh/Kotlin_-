package com.example.coinmonitoring.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coinmonitoring.db.entity.SelectedCoinPriceEntity
import com.example.coinmonitoring.network.model.RecentCoinPriceList
import com.example.coinmonitoring.repository.DBRepository
import com.example.coinmonitoring.repository.NetWorkRepository
import timber.log.Timber
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date

//최근 거래된 코인 가격 내역을 가져오는 WorkManager

// 1. 관심있는 코인 리스트 가져오기
// 2. 해당 코인의 가격 변동 정보를 가져온다 (api 추가 세팅)
// 3. 해당 코인의 가격 변동 정보를 DB에 저장


class GetCoinPriceRecentContractedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {
        Timber.d("dowork")
        getAllInterestSelectedCoinData()

        return Result.success()
    }
    // 1. 관심있는 코인 리스트 가져오기
    suspend fun getAllInterestSelectedCoinData(){
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        //시간 체크 용
        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList){
            // 2. 해당 코인의 가격 변동 정보를 가져온다 (api 추가 세팅)
            val recentCoinPirceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)
            Timber.d(recentCoinPirceList.toString())

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPirceList,
                timeStamp
            )
        }
    }

    // 3. 해당 코인의 가격 변동 정보를 DB에 저장
    fun saveSelectedCoinPrice(
        coinName : String,
        recentCoinPriceList: RecentCoinPriceList,
        timestamp: Date
    ){
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timestamp
        )
        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }
}
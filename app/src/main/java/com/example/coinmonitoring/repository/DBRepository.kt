package com.example.coinmonitoring.repository

import com.example.coinmonitoring.App
import com.example.coinmonitoring.db.DataBase
import com.example.coinmonitoring.db.dao.InterestCoinDAO
import com.example.coinmonitoring.db.entity.InterestCoinEntity
import com.example.coinmonitoring.db.entity.SelectedCoinPriceEntity

class DBRepository {

    //전역으로 설정한 context에서 db 호출
    val context = App.context()
    val db = DataBase.getDatabase(context)

    //InsertsCoin

    //전체 코인 데이터 가져오기
    fun getAllInterestCoinData() = db.interestCoinDAO().getAllData()

    //코인 데이터 넣기
    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDAO().insert(interestCoinEntity)

    //코인 데이터 업데이트
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDAO().update(interestCoinEntity)

    //사용자가 관심있어한 코인만 가져오기
    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    // CoinPrice
    fun getAllCoinPriceData() = db.selectedCoinDAO().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) =
        db.selectedCoinDAO().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinDAO().getOneCoinData(coinName)
}
package com.example.coinmonitoring.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinmonitoring.db.entity.SelectedCoinPriceEntity
@Dao
interface SelectedCoinPriceDAO {
    // getAllData - 전체 호출
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    // insert - 데이터 입력
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해서 저장된 정보를 가져오는 Detail 쿼리
    // 현재가격 , 15분전, 30분전, 45분전 의 변화를 DB의 값과 비교하는 용도
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName : String) : List<SelectedCoinPriceEntity>
}
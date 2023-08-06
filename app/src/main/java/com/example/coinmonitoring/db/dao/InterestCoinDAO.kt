package com.example.coinmonitoring.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.coinmonitoring.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InterestCoinDAO {
    //getAllData - 전체 데이터 호출
    //데이터의 변경 사항을 감지하기 좋다.
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : Flow<List<InterestCoinEntity>>

    //Insert - DB에 데이터를 집어 넣는경우
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    //Update
    //데이터 취소나 새로운 선택을 가능하게 함
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    //getSeletecCoinList - 즐겨찾기 리스트 호출
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>
}
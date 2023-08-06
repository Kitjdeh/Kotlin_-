package com.example.coinmonitoring.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coinmonitoring.db.dao.InterestCoinDAO
import com.example.coinmonitoring.db.dao.SelectedCoinPriceDAO
import com.example.coinmonitoring.db.entity.DateConverters
import com.example.coinmonitoring.db.entity.InterestCoinEntity
import com.example.coinmonitoring.db.entity.SelectedCoinPriceEntity


@Database(entities = [InterestCoinEntity::class, SelectedCoinPriceEntity::class], version = 2)
@TypeConverters(DateConverters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun interestCoinDAO(): InterestCoinDAO
    abstract fun selectedCoinDAO() : SelectedCoinPriceDAO

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(
            context: Context
        ): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "coin_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
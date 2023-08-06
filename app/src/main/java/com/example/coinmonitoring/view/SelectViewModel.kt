package com.example.coinmonitoring.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmonitoring.dataModel.CurrentPrice
import com.example.coinmonitoring.dataModel.CurrentPriceResult
import com.example.coinmonitoring.dataStore.MyDataStore
import com.example.coinmonitoring.db.entity.InterestCoinEntity
import com.example.coinmonitoring.repository.DBRepository
import com.example.coinmonitoring.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()

    private val dbRepository = DBRepository()

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    //LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPirceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val save : LiveData<String>
        get() = _saved


    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()
        currentPriceResultList = ArrayList()
        for (data in result.data) {
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(data.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(data.key, gsonFromJson)

//                Timber.d(currentPriceResult.toString())
                currentPriceResultList.add(currentPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }
        }
        _currentPriceResult.value = currentPriceResultList
    }


    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    //DB에 데이터 저장
    //IO 디스패쳐의 특징 -> 코어 수 보다 더 많은 쓰레드를 가지는 스레드 풀, IO작업은 CPU소모가 덜함
    //IO -> Room 구성요소에 좋다는게 공식문서 추천
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
        viewModelScope.launch(Dispatchers.IO) {
            //1. 전체 코인 데이터를 가져온다 ( = currentPriceResultList)
            for (coin in currentPriceResultList) {
                //2. 내가 선택한 코인데이터와 비선택 구분 -> selectedRVAdapter에서 저장하고있었음
                val selected = selectedCoinList.contains(coin.coinName)
                //3. 저장
                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                    selected
                )
                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            }

            withContext(Dispatchers.Main)
            {
                _saved.value = "done"
            }

        }
}
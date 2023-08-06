package com.example.coinmonitoring.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinmonitoring.dataModel.UpDownDataSet
import com.example.coinmonitoring.db.entity.InterestCoinEntity
import com.example.coinmonitoring.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>

    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()

    val arr15min: LiveData<List<UpDownDataSet>>
        get() = _arr15min
    val arr30min: LiveData<List<UpDownDataSet>>
        get() = _arr30min
    val arr45min: LiveData<List<UpDownDataSet>>
        get() = _arr45min

    //CoinListFragment
    fun getAllInterestCoinData() = viewModelScope.launch {

        //라이브 데이터로 활용하기 좋게 수정
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            if (interestCoinEntity.selected) {
                interestCoinEntity.selected = false
            } else {
                interestCoinEntity.selected = true
            }
            dbRepository.updateInterestCoinData(interestCoinEntity)
        }

    //PriceChangeFragment
    //1. 즐겨찾기한 코인 리스트 호출
    //2. 즐겨찾기 코인 리스트의 반복문으로 하나씩 순회
    //3. 해당 코인의 저장된 가격 리스트 호출
    //4. 시간대 별로 어떻게 변경되었는지 알려주는 로직 작성
    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {
        //1. 즐겨찾기한 코인 리스트 호출
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        // 변화 관찰을 위한 데이터리스트 작성
        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        //2. 즐겨찾기 코인 리스트의 반복문으로 하나씩 순회
        for (data in selectedCoinList) {

            //3. 해당 코인의 저장된 가격 리스트 호출
            val coinName = data.coin_name // 저장된 DB를 찾기 위해 이름 값 호출
            // 시간 순으로 저장되니 역순으로 하면 최신값부터 확인 가능
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()
            val size = oneCoinData.size
            if (size > 1) {
                //DB에 값이 2개 이상
                // 15분전 가격 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)
            }
            if (size > 2) {
                //DB에 값이 3개 이상
                // 30분전 가격 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)
            }
            if (size > 3) {
                //DB에 값이 4개 이상
                // 45분전 가격 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)
            }
        }
        //백그라운드에서 실행하면 에러발생하니 Dispatchers로 쓰레드 변경
        withContext(Dispatchers.Main) {
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }
    }
}
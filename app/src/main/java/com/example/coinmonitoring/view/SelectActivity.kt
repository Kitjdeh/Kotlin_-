package com.example.coinmonitoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.coinmonitoring.background.GetCoinPriceRecentContractedWorkManager
import com.example.coinmonitoring.view.main.MainActivity
import com.example.coinmonitoring.databinding.ActivitySelectBinding
import com.example.coinmonitoring.view.adpater.SelectRVAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding

    private val viewModel: SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList()
        viewModel.currentPirceResult.observe(this, {

            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)
        })


        binding.laterTextArea.setOnClickListener {
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, {
            if (it.equals("done")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // 가장 처음으로 저장한 코인 정보가 시작 되는 시점
                saveInterestCoinDatePeriodic()
            }
        })
    }

    private fun saveInterestCoinDatePeriodic() {
        // 주기적으로 workmanager 를 돌릴 수 있게 세팅
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            //얼마나의 주기로?
            15,
            TimeUnit.MINUTES
        ).build()
        //여러개가 아니라 한개만 돌아 가도록 세팅
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}
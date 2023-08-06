package com.example.coinmonitoring.view.adpater

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinmonitoring.R
import com.example.coinmonitoring.dataModel.CurrentPriceResult

class SelectRVAdapter(val context: Context, val coinPriceList: List<CurrentPriceResult>) :
    RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName: TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown: TextView = view.findViewById(R.id.coinPriceUpDown)
        val coinPriceDelta: TextView = view.findViewById(R.id.coinPriceDelta)
        val likeImage: ImageView = view.findViewById(R.id.likeBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRVAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectRVAdapter.ViewHolder, position: Int) {

        holder.coinName.text = coinPriceList[position].coinName
        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H
        val coinDeltaPercentage =  coinPriceList[position].coinInfo.fluctate_rate_24H
        holder.coinPriceDelta.text = coinDeltaPercentage
        if (fluctate_24H.contains("-")) {
            holder.coinPriceUpDown.text = "영!차! 영!차!"
            //색깔 변경
            holder.coinPriceUpDown.setTextColor(Color.RED)
            holder.coinPriceDelta.setTextColor(Color.RED)
        } else {
            holder.coinPriceUpDown.text = "저점매수의 기회?"
            holder.coinPriceUpDown.setTextColor(Color.BLUE)
            holder.coinPriceDelta.setTextColor(Color.BLUE)
        }

        val likeImage = holder.likeImage

        //현재 클릭한 코인 확인
        val currentCoin = coinPriceList[position].coinName

        //view를 그려줄 때
        if(selectedCoinList.contains(currentCoin)) {
            likeImage.setImageResource(R.drawable.like_red)
        }
        else{
            likeImage.setImageResource(R.drawable.like_grey)
        }

        //이미지를 클릭하면
        likeImage.setOnClickListener {
            // 기존의 selectedCoinList에 있다면??
            if (selectedCoinList.contains(currentCoin)) {
                likeImage.setImageResource(R.drawable.like_grey)
                selectedCoinList.remove(currentCoin)
                // 기존의 selectedCoinList에 없다면?
            } else {
                selectedCoinList.add(currentCoin)
                likeImage.setImageResource(R.drawable.like_red)
            }


        }
    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }
}
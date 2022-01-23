package com.soten.coinexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.soten.coinexample.databinding.ItemCoinBinding
import com.soten.coinexample.response.Coin

class CoinAdapter : ListAdapter<Coin, CoinViewHolder>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CoinViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun submitData(coin: Coin) {
        val test = mutableListOf<Coin>()
        when (coin.code) {
            "KRW-BTC" -> {
                test.addAll(currentList)
                test[0] = coin
                coin.isIncrease = when {
                    currentList[0].tradePrice > coin.tradePrice -> {
                        true
                    }
                    currentList[0].tradePrice == coin.tradePrice -> {
                        null
                    }
                    currentList[0].tradePrice < coin.tradePrice -> {
                        false
                    }
                    else -> null
                }
                submitList(test)
                notifyItemChanged(CoinPosition.BTC.position)
            }
            "KRW-XRP" -> {
                test.addAll(currentList)
                test[1] = coin
                coin.isIncrease = when {
                    currentList[1].tradePrice > coin.tradePrice -> {
                        true
                    }
                    currentList[1].tradePrice == coin.tradePrice -> {
                        null
                    }
                    currentList[1].tradePrice < coin.tradePrice -> {
                        false
                    }
                    else -> null
                }
                submitList(test)
                notifyItemChanged(CoinPosition.XRP.position)
            }
            "BTC-GXC" -> {
                test.addAll(currentList)
                test[2] = coin
                coin.isIncrease = when {
                    currentList[2].tradePrice > coin.tradePrice -> {
                        true
                    }
                    currentList[2].tradePrice == coin.tradePrice -> {
                        null
                    }
                    currentList[2].tradePrice < coin.tradePrice -> {
                        false
                    }
                    else -> null
                }
                submitList(test)
                notifyItemChanged(CoinPosition.SAND.position)
            }
        }
    }

    enum class CoinPosition(val position: Int) {
        BTC(0),
        XRP(1),
        SAND(2)
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.code == newItem.code
            }
        }
    }

}


package com.soten.coinexample.adapter

import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.soten.coinexample.R
import com.soten.coinexample.databinding.ItemCoinBinding
import com.soten.coinexample.response.Coin
import java.text.DecimalFormat

class CoinViewHolder(
    private val binding: ItemCoinBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: Coin) {
        val listener = object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                binding.stroke.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        }

        when (coin.isIncrease) {
            null -> {
                binding.stroke.visibility = View.GONE
            }
            false -> {
                binding.stroke.visibility = View.VISIBLE
                binding.stroke.badgeColor = Color.RED
                val animation = android.view.animation.AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.blink_animation
                )
                animation.setAnimationListener(listener)
                binding.stroke.startAnimation(animation)
            }
            true -> {
                binding.stroke.visibility = View.VISIBLE
                binding.stroke.badgeColor = Color.BLUE
                val animation = android.view.animation.AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.blink_animation
                )
                animation.setAnimationListener(listener)
                binding.stroke.startAnimation(animation)

            }
        }
        val bt = DecimalFormat("#,###.################################")

        binding.coinPrice.text = bt.format(coin.tradePrice)
        binding.coinName.text = coin.code
    }

}
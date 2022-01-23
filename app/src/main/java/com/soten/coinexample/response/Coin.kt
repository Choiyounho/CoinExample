package com.soten.coinexample.response


import com.google.gson.annotations.SerializedName

data class Coin(
//    @SerializedName("ask_bid")
//    val askBid: String,
    @SerializedName("change")
    val change: String,
    @SerializedName("change_price")
    val changePrice: Double,
    @SerializedName("code")
    val code: String,
    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double,
    @SerializedName("sequential_id")
    val sequentialId: Long,
    @SerializedName("stream_type")
    val streamType: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("trade_date")
    val tradeDate: String,
    @SerializedName("trade_price")
    val tradePrice: Double,
    @SerializedName("trade_time")
    val tradeTime: String,
    @SerializedName("trade_timestamp")
    val tradeTimestamp: Long,
    @SerializedName("trade_volume")
    val tradeVolume: Double,
    @SerializedName("type")
    val type: String,
    var isIncrease: Boolean?
)
package com.soten.coinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.soten.coinexample.adapter.CoinAdapter
import com.soten.coinexample.databinding.ActivityMainBinding
import com.soten.coinexample.response.Coin
import okhttp3.*
import okio.ByteString

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val upbitSocket = UpbitWsClient()

    private val coinAdapter by lazy { CoinAdapter() }

    private val tl = mutableListOf(
        Coin(
            change = "RISE",
            changePrice = 689000.0,
            code = "KRW - BTC",
            prevClosingPrice = 4.3375E7,
            sequentialId = 1642929710000000,
            streamType = "REALTIME",
            timestamp = 1642929710144,
            tradeDate = "2022 - 01 - 23",
            tradePrice = 4.4064E7,
            tradeTime = "09:21:50",
            tradeTimestamp = 1642929710000,
            tradeVolume = 0.00396814,
            type = "trade",
            isIncrease = null
        ),
        Coin(
            change = "RISE",
            changePrice = 689000.0,
            code = "KRW - BTC",
            prevClosingPrice = 4.3375E7,
            sequentialId = 1642929710000000,
            streamType = "REALTIME",
            timestamp = 1642929710144,
            tradeDate = "2022 - 01 - 23",
            tradePrice = 4.4064E7,
            tradeTime = "09:21:50",
            tradeTimestamp = 1642929710000,
            tradeVolume = 0.00396814,
            type = "trade",
            isIncrease = null
        ),
        Coin(
            change = "RISE",
            changePrice = 689000.0,
            code = "KRW - BTC",
            prevClosingPrice = 4.3375E7,
            sequentialId = 1642929710000000,
            streamType = "REALTIME",
            timestamp = 1642929710144,
            tradeDate = "2022 - 01 - 23",
            tradePrice = 4.4064E7,
            tradeTime = "09:21:50",
            tradeTimestamp = 1642929710000,
            tradeVolume = 0.00396814,
            type = "trade",
            isIncrease = null
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.rvCoin.adapter = coinAdapter
        binding.rvCoin.itemAnimator = null

        coinAdapter.submitList(tl)

//        lifecycleScope.launch {
//            delay(1000L)
//            tl[0] = Coin("KRW-BTC", 123124.0)
//
//            coinAdapter.submitData(coin = Coin("KRW-BTC", 123124.0))
//            coinAdapter.submitList(tl)
//            coinAdapter.notifyItemChanged(0)
//        }


        upbitSocket.startListenOrderbook()
    }

    override fun onDestroy() {
        super.onDestroy()
        upbitSocket.close()
    }

    inner class UpbitWsClient {

        private val gson = Gson()
        private var webSocketList: MutableList<WebSocket> = mutableListOf()

        private val orderbookListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket?, response: Response?) {
                Log.d("CoinTest", "업비트 onOpen")

                webSocket?.send(createOrderbookTicket())
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("CoinTest", "onFail")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("CoinTest", "onClosed")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)

                val bytesToString = bytes.toByteArray().decodeToString()
                val coin = gson.fromJson(bytesToString, Coin::class.java)

                runOnUiThread {
                    coinAdapter.submitData(coin)
                }
                Log.d("CoinTest", "$coin")
            }
        }

        fun startListenOrderbook() {
            Log.d("CoinTest", "시작")

            val request = Request.Builder()
                .url("wss://api.upbit.com/websocket/v1")
                .build()
            val okHttpClient = OkHttpClient()

            val webSocket = okHttpClient.newWebSocket(request, orderbookListener)
            webSocketList.add(webSocket)
        }

        fun createOrderbookTicket(): String {
            val ticket = Ticket("test")
            val codeList = arrayListOf("BTC-GXC", "KRW-BTC", "KRW-XRP")
            val type = Type("trade", codeList)

            return gson.toJson(arrayListOf(ticket, type))
        }

        fun close() {
            webSocketList.forEach { it.close(1000, "bye") }
        }

    }
}

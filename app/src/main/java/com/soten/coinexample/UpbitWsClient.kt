package com.soten.coinexample

import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okio.ByteString

class UpbitWsClient {

    private val gson = Gson()
    private var webSocketList: MutableList<WebSocket> = mutableListOf()

    val orderbookListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket?, response: Response?) {
            println("onOpen, $response")

            webSocket?.send(createOrderbookTicket())
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d("CoinTest", "업비트 실패")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d("CoinTest", "업비트 클로즈")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            Log.d("CoinTest", "업비트 실시간 : ${bytes.toByteArray().decodeToString()}")
//            val bytesToString = bytes.toByteArray().decodeToString()
//            val json = gson.toJson(bytesToString)
//            val coin = gson.fromJson(bytesToString, Coin::class.java)
//            Log.d("GsonTest", "실시간 객 : ${coin}")
        }

    }

    fun startListenOrderbook() {
        Log.d("CoinTest", "시작")

        val request = Request.Builder()
            .url("https://api.upbit.com/websocket/v1")
            .build()
        val okHttpClient = OkHttpClient()

        val webSocket = okHttpClient.newWebSocket(request, orderbookListener)
        webSocketList.add(webSocket)
    }


    fun createOrderbookTicket(): String {
        val ticket = Ticket("test")
        val codeList = arrayListOf("KRW-SAND", "KRW-BTC", "KRW-XRP")
        val type = Type("trade", codeList)

        return gson.toJson(arrayListOf(ticket, type))
    }

    fun close() {
        webSocketList.forEach { it.close(1000, "bye") }
    }

}

data class Ticket(val ticket: String)
data class Type(val type: String, val codes: List<String>)
package com.example.rd.baomingxitong.fuzhu

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import com.example.rd.baomingxitong.Http.LoadInterceptor
import com.example.rd.baomingxitong.ViewModel.LiaoTianShiVM
import com.example.rd.baomingxitong.base.GreenDaoUse
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.entity.HttpResult
import com.example.rd.baomingxitong.entity.LiaoTian.LTMsg
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.io.StringReader
import java.lang.Exception
import java.net.URI
import java.net.URISyntaxException

/**
 * Created by rd on 2017/11/4.
 */

object SocketClient
{
    var Client: WebSocketClient ?=null
    var address = "ws://10.0.2.2:8080/ws" // ws://111.230.17.38/baomingxitong/ws   ws://10.0.2.2:8080/ws
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0x111) {
                var news: String? = ""
                news = msg.data.getString("news")
                Log.d("news",news)
            }
        }
    }

    fun init()
    {
        if (Client==null)
        {
            val sp = MyApp.instances.getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE)
            var headers = mapOf(Pair("Cookie",sp.getString("bmcookies", "")))
            Client = object :WebSocketClient(URI(address),Draft_6455(),headers,1000000){
                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Log.i("LOG", "Connection closed by " + (if (remote) "remote peer" else "us") + ", info=" + reason)
                    //
                    closeConnect()
                }

                override fun onError(ex: Exception?) {
                    Log.i("LOG", "error:" + ex)
                }

                override fun onMessage(message: String?) {
                    try {
                        var gson: Gson = Gson()
                        var msg = gson.fromJson(message,HttpResult::class.java)
                        if (msg.code==666)
                        {
                            var reader: JsonReader = JsonReader(StringReader(msg.data.toString()))
                            reader.isLenient = true
                            Log.d("msgDATA:",msg.data.toString())
                            var LTmsg:LTMsg = gson.fromJson(gson.toJson(msg.data),LTMsg::class.java)
                            Log.d("LTMSG",LTmsg.toString())
                           Observable
                                   .empty<LTMsg>()
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .doOnComplete {
                                       LiaoTianShiVM.setMsg(LTmsg)
                                   }
                                   .subscribe()
                        }
                    }
                   catch (e: Exception)
                   {
                       e.printStackTrace()
                   }

                }

                override fun onOpen(handshakedata: ServerHandshake?) {
                    Log.i("LOG", "opened connection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                }
            }
            Client!!.connect()
        }
    }

    fun closeConnect() {
        try {
            Client!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            Client = null
        }
    }

    fun sendMsg(msg: String) {

        Client!!.send(msg)
    }
}
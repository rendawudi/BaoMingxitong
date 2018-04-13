package com.example.rd.baomingxitong.Http

import android.content.Context
import android.content.SharedPreferences
import android.net.Network
import android.text.TextUtils
import android.util.Log
import com.example.rd.baomingxitong.Model.UserModel
import com.example.rd.baomingxitong.base.MyApp
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by rd on 2017/9/27.
 */

class ReceiveInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        //set-cookie可能为多个
        if (!response.headers("set-cookie").isEmpty()) {
            val cookies = response.headers("set-cookie")
            val cookie = encodeCookie(cookies)
            saveCookie( cookie)
        }

        return response
    }

    //整合cookie为唯一字符串
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        for (cookie in cookies) {
            val arr = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in arr) {
                if (set.contains(s)) continue
                set.add(s)

            }
        }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }
    private fun saveCookie(cookies: String) {
        val sp = MyApp.instances.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
        val editor = sp.edit()


            editor.putString("bmcookies", cookies)

        editor.apply()

    }

    companion object {

        private val COOKIE_PREF = "cookies_prefs"
    }
}




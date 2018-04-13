package com.example.rd.baomingxitong.Http

import android.content.Context
import android.text.TextUtils
import com.example.rd.baomingxitong.base.MyApp
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by rd on 2017/9/30.
 */
class  LoadInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val cookie = getCookie()
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie!!)
        }

        return chain.proceed(builder.build())
    }

    private fun getCookie(): String? {
        val sp = MyApp.instances.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
            return sp.getString("bmcookies", "")
    }

    companion object {

        private val COOKIE_PREF = "cookies_prefs"
    }
}
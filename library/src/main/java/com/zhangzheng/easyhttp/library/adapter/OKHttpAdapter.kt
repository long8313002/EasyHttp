package com.zhangzheng.easyhttp.library.adapter

import com.zhangzheng.easyhttp.library.EasyHttp
import com.zhangzheng.easyhttp.library.IResponseParse
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request


class OKHttpAdapter(var parse: IResponseParse) : EasyHttp.IAdapter {
    override suspend fun request(url: String, params: Map<String, String>, isGet: Boolean): String {
        val okHttpClient = OkHttpClient()
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        val request: Request = Request.Builder().url(url).method(
            if (isGet) "GET" else "POST",
            null
        ).build()
        //3.创建一个call对象,参数就是Request请求对象
        //3.创建一个call对象,参数就是Request请求对象
        val call: Call = okHttpClient.newCall(request)
        return call.execute().body()?.string()?:""
    }

    override fun <T> parse(value: String, clazz: Class<T>)=parse.parse(value,clazz)
}
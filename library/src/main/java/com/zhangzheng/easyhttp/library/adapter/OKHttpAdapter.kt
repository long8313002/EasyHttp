package com.zhangzheng.easyhttp.library.adapter

import com.zhangzheng.easyhttp.library.EasyHttp
import com.zhangzheng.easyhttp.library.IResponseParse
import okhttp3.*


class OKHttpAdapter(var parse: IResponseParse) : EasyHttp.IAdapter {
    override suspend fun request(url: String, params: Map<String, String>, isGet: Boolean): String {
        val okHttpClient = OkHttpClient()
        val formBody = FormBody.Builder()
        params.forEach {
            formBody.add(it.key,it.value)
        }

        val request: Request =if(isGet){
             Request.Builder().url(url.urlWithParam(params)).method("GET", null)
        }else{
             Request.Builder().url(url).method("POST", createParamBody(params))
        }.build()

        val call: Call = okHttpClient.newCall(request)
        return call.execute().body()?.string()?:""
    }

    override fun <T> parse(value: String, clazz: Class<T>)=parse.parse(value,clazz)


    private fun createParamBody(params: Map<String, String>):FormBody{
        val formBody = FormBody.Builder()
        params.forEach {
            formBody.add(it.key,it.value)
        }
        return formBody.build()
    }
}
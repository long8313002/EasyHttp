package com.zhangzheng.easyhttp.library.adapter

import com.zhangzheng.easyhttp.library.EasyHttp.IAdapter
import com.zhangzheng.easyhttp.library.IResponseParse
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.Continuation

class DefaultAdapter(var parse: IResponseParse) : IAdapter {

    override suspend fun request(url: String, params: Map<String, String>, isGet: Boolean): String {
        return try {
            requestImpl(url, params, isGet)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    @Throws(Exception::class)
    private fun requestImpl(urlPath: String, params: Map<String, String>, isGet: Boolean): String {
        val requestMethod:String
        val url:URL
        val postParam:String

        if(isGet){
            requestMethod = "GET"
            url = URL(urlPath.urlWithParam(params))
            postParam = ""
        }else{
            requestMethod = "POST"
            url = URL(urlPath)
            postParam = createJsonParam(params)
        }

        val httpConn = url.openConnection() as HttpURLConnection
        httpConn.doOutput = true
        httpConn.doInput = true
        httpConn.useCaches = false //不允许缓存
        httpConn.requestMethod = requestMethod
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        httpConn.setRequestProperty("Connection", "Keep-Alive")
        httpConn.setRequestProperty("Charset", "UTF-8")
        httpConn.connect()
        val dos = DataOutputStream(httpConn.outputStream)
        dos.writeBytes(postParam)
        dos.flush()
        dos.close()
        val resultCode = httpConn.responseCode
        if (HttpURLConnection.HTTP_OK == resultCode) {
            val sb = StringBuffer()
            var readLine: String?
            val responseReader = BufferedReader(
                InputStreamReader(httpConn.inputStream, "UTF-8")
            )
            while (responseReader.readLine().also { readLine = it } != null) {
                sb.append(readLine).append("\n")
            }
            responseReader.close()
            return sb.toString()
        }
        return ""
    }

    private fun createJsonParam(params: Map<String, String>): String {
        if (params.isNullOrEmpty()) {
            return "{}"
        }
        val jo = JSONObject()
        params.forEach {
            jo.put(it.key, it.value)
        }
        return jo.toString()
    }

    override fun <T> parse(value: String, clazz: Class<T>) = parse.parse(value,clazz)

}
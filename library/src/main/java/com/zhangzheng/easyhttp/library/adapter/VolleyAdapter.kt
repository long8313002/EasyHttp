package com.zhangzheng.easyhttp.library.adapter

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.zhangzheng.easyhttp.library.EasyHttp
import com.zhangzheng.easyhttp.library.IResponseParse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.CountDownLatch


class VolleyAdapter(var context: Context, var parse: IResponseParse) : EasyHttp.IAdapter {

    override suspend fun request(url: String, params: Map<String, String>, isGet: Boolean) =
        withContext(Dispatchers.IO) {

            val lock = CountDownLatch(1)

            val mQueue = Volley.newRequestQueue(context)

            var content: String? = ""

            val stringRequest = StringRequest(url, Response.Listener {
                content = it
                lock.countDown()
            }, Response.ErrorListener {
                lock.countDown()
            })

            try {
                mQueue.add(stringRequest)
                lock.await()
            }catch (e:Exception){

            }


            content ?: ""
        }

    override fun <T > parse(value: String, clazz: Class<T>) =
        parse.parse(value, clazz)


}
package com.zhangzheng.easyhttp.library

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <reified T> IReq.request(): T {

    val url = this.javaClass.getAnnotation(URL::class.java)?.value ?: ""
    val isPost = this.javaClass.getAnnotation(POST::class.java) != null
    val isGet = this.javaClass.getAnnotation(GET::class.java) != null

    return withContext(Dispatchers.IO){
        EasyHttp.request(this@request, url, T::class.java, (isGet || !isPost))
    }

}
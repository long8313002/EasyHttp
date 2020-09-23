package com.zhangzheng.easyhttp.library


object EasyHttp {

    interface IAdapter : IRequestExecute, IResponseParse{
         fun String.urlWithParam(params:Map<String,String>):String{
             if (params.isEmpty()){
                 return this
             }
             val urlBuild = StringBuilder(this)
             urlBuild.append("?")
             params.forEach {
                 urlBuild.append("${it.key}=${it.value}")
                 urlBuild.append("&")
             }
             urlBuild.deleteCharAt(urlBuild.length-1)
             return urlBuild.toString()
         }
    }

    lateinit var executeRequest: IRequestExecute
    lateinit var parseResponse: IResponseParse

    fun init(adapter: IAdapter) {
        executeRequest = adapter
        parseResponse = adapter
    }


    suspend inline fun <reified T > request(
        req: IReq,
        url: String,
        responseClazz: Class<T>,
        isGet: Boolean = true
    ): T? {
        val responseString = executeRequest.request(url, params(req), isGet)
        return parseResponse.parse(responseString, responseClazz)
    }

    fun params(req: IReq): Map<String, String> {
        val params = mutableMapOf<String, String>()
        req.javaClass.declaredFields.forEach {
            it.isAccessible = true
            val value = it.get(req)
            if (value != null) {
                params[it.name] = value.toString()
            }
        }
        return params
    }

}
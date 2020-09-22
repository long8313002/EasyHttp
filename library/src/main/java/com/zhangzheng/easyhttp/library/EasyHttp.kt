package com.zhangzheng.easyhttp.library


object EasyHttp {

    interface IAdapter : IRequestExecute, IResponseParse

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
    ): T {
        val responseString = executeRequest.request(url, params(req), isGet)
        return parseResponse.parse(responseString, responseClazz)
    }

    fun params(req: IReq): Map<String, String> {
        val params = mutableMapOf<String, String>()
        req.javaClass.fields.forEach {
            val value = it.get(req)
            if (value != null) {
                params[it.name] = value.toString()
            }
        }
        return params
    }

}
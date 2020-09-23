package com.zhangzheng.easyhttp.library.adapter.parse

import com.google.gson.Gson
import com.zhangzheng.easyhttp.library.IResponseParse

class GsonJsonParse :IResponseParse{
    override fun <T> parse(value: String, clazz: Class<T>): T {
        return Gson().fromJson(value,clazz)
    }

}
package com.zhangzheng.easyhttp.library

interface IResponseParse {

    fun <T > parse(value: String,clazz: Class<T>): T

}
package com.zhangzheng.easyhttp.library.adapter.parse

import com.alibaba.fastjson.JSON
import com.zhangzheng.easyhttp.library.IResponseParse

class FastJsonParse :IResponseParse{

    override fun <T > parse(value: String, clazz: Class<T>)=JSON.parseObject(value,clazz)

}
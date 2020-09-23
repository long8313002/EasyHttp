package com.zhangzheng.easyhttp

import com.zhangzheng.easyhttp.library.IReq
import com.zhangzheng.easyhttp.library.GET
import com.zhangzheng.easyhttp.library.POST
import com.zhangzheng.easyhttp.library.URL

@URL("https://suggest.taobao.com/sug")
@GET
data class BaiDuReq(var code: String = "utf-8", val q: String) : IReq


class BaiDuResponse {
    var result: List<Any>? = null
}
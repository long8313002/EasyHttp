package com.zhangzheng.easyhttp

import com.zhangzheng.easyhttp.library.IReq
import com.zhangzheng.easyhttp.library.GET
import com.zhangzheng.easyhttp.library.URL

@URL("http://www.weather.com.cn/data/cityinfo/101010100.html")
@GET
data class BaiDuReq(var orderId:String ) :IReq


class BaiDuResponse  {
    var weatherinfo = ""
}
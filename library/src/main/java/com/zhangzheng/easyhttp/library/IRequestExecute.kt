package com.zhangzheng.easyhttp.library

 interface IRequestExecute{

    suspend fun request(url:String,params:Map<String,String>,isGet:Boolean):String
}
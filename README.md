排版地址：https://blog.csdn.net/long8313002/article/details/108751307


概述
         最近又要替换网络库成了，导致所有的业务代码都要替换且要重新测试回归。为了防止后续再遇到这样事情，降低替换核心库的成本，现利用协程对网络库进行封装。

 

理想API调用方式
     launch {
            val response = TestReq(q = "电脑").request<TestResponse>()
            Toast.makeText(this, response?.result?.toString()?:"网络异常", Toast.LENGTH_SHORT).show()
            }
请求定义
@URL("https://suggest.taobao.com/sug")
@GET
data class TestReq(var code: String = "utf-8", val q: String) : IReq
 
 
class TestResponse {
    var result: List<Any>? = null
}
说明
    launch是对Context的类扩展，对于Activity而言可以在销毁的时候关闭协程；其他场景可以自己管理协程。例如：

import kotlinx.coroutines.launch
 
class MainActivity : AppCompatActivity() , CoroutineScope by MainScope() {
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch {
            val response = TestReq(q = "电脑").request<TestResponse>()
            Toast.makeText(this@MainActivity, response?.result?.toString()?:"网络异常", Toast.LENGTH_SHORT).show()
        }
    }
 
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
 
}
 

使用
引用
  implementation 'com.zhangzheng.easyhttp:library:1.0.1'
初始化
默认（HttpUrlConnection）

EasyHttp.init(DefaultAdapter(GsonJsonParse()))
 implementation 'com.google.code.gson:gson:2.8.6'
OkHttp

 EasyHttp.init(OKHttpAdapter(GsonJsonParse()))
    implementation 'com.squareup.okhttp3:okhttp:3.8.0'
    implementation 'com.squareup.okio:okio:1.12.0'
 
 implementation 'com.google.code.gson:gson:2.8.6'
Vollery

EasyHttp.init(VolleyAdapter(this,GsonJsonParse()))
    implementation 'com.android.volley:volley:1.1.1'
 
 implementation 'com.google.code.gson:gson:2.8.6'
如果使用的是FastJson可以替换解析器

GsonJsonParse() --> FastJsonParse()
  implementation 'com.alibaba:fastjson:1.2.37'
如果协程不可用，检查协程库是否引入了

 implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1"
 

自定义扩展
        自己扩展可以实现EasyHttp.IAdapter，对于后续替换网络请求核心库只需要开发适配器就可以了。对于json解析可以实现IResponseParse来扩展。示例如下：

 

IAdapter

class OKHttpAdapter(var parse: IResponseParse) : EasyHttp.IAdapter {
    override suspend fun request(url: String, params: Map<String, String>, isGet: Boolean): String {
        val okHttpClient = OkHttpClient()
        val formBody = FormBody.Builder()
        params.forEach {
            formBody.add(it.key,it.value)
        }
 
        val request: Request =if(isGet){
             Request.Builder().url(url.urlWithParam(params)).method("GET", null)
        }else{
             Request.Builder().url(url).method("POST", createParamBody(params))
        }.build()
 
        val call: Call = okHttpClient.newCall(request)
        return call.execute().body()?.string()?:""
    }
 
    override fun <T> parse(value: String, clazz: Class<T>)=parse.parse(value,clazz)
 
 
    private fun createParamBody(params: Map<String, String>):FormBody{
        val formBody = FormBody.Builder()
        params.forEach {
            formBody.add(it.key,it.value)
        }
        return formBody.build()
    }
}
 

IResponseParse

class FastJsonParse :IResponseParse{
 
    override fun <T > parse(value: String, clazz: Class<T>)=JSON.parseObject(value,clazz)
 
}
 

github地址：https://github.com/long8313002/EasyHttp

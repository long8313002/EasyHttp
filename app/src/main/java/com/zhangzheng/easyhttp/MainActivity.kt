package com.zhangzheng.easyhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zhangzheng.easyhttp.library.EasyHttp
import com.zhangzheng.easyhttp.library.adapter.OKHttpAdapter
import com.zhangzheng.easyhttp.library.adapter.parse.FastJsonParse
import com.zhangzheng.easyhttp.library.launch
import com.zhangzheng.easyhttp.library.request

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyHttp.init(OKHttpAdapter(FastJsonParse()))

        launch {
            val response = BaiDuReq("1111").request<BaiDuResponse>()
            Toast.makeText(this@MainActivity, response.weatherinfo, Toast.LENGTH_SHORT).show()
        }
    }

}

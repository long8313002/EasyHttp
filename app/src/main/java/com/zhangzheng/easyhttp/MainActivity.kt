package com.zhangzheng.easyhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zhangzheng.easyhttp.library.EasyHttp
import com.zhangzheng.easyhttp.library.adapter.DefaultAdapter
import com.zhangzheng.easyhttp.library.adapter.OKHttpAdapter
import com.zhangzheng.easyhttp.library.adapter.VolleyAdapter
import com.zhangzheng.easyhttp.library.adapter.parse.GsonJsonParse
import com.zhangzheng.easyhttp.library.launch
import com.zhangzheng.easyhttp.library.request

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyHttp.init(DefaultAdapter(GsonJsonParse()))

        launch {
            val response = TestReq(q = "电脑").request<TestResponse>()
            Toast.makeText(this, response?.result?.toString()?:"网络异常", Toast.LENGTH_SHORT).show()
        }
    }

}

package demo.vicwang.myapplication.mvp.model

import android.util.Log
import demo.vicwang.myapplication.mvp.presenter.MainBridge
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiRepository : MainBridge.Model {
    private val mHouseData = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a"

    private val mAnimalData = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=a3e2b221-75e0-45c1-8f97-75acbd43d613&q="

    override fun getHouseData(callback: ApiCallback) {
        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder()
                .url(mHouseData)
                .method("GET", null)
                .build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed("fail", e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var str = ""
                if (response.cacheResponse() != null) {
                    str = response.cacheResponse().toString()
                    Log.d("okhttp", "cache---$str")

                } else {
                    str = response.body().string()
                    Log.d("okhttp", "network---$str")
                }
                callback.onSuccess(str)
            }
        })
    }

    override fun getAnimalData(targetArea: String, callback: ApiCallback) {
        val url = mAnimalData + targetArea

        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder()
                .url(url)
                .method("GET", null)
                .build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed("fail", e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var str = ""
                if (response.cacheResponse() != null) {
                    str = response.cacheResponse().toString()
                    Log.d("okhttp", "cache---$str")

                } else {
                    str = response.body().string()
                    Log.d("okhttp", "network---$str")
                }
                callback.onSuccess(str)
            }
        })
    }


}
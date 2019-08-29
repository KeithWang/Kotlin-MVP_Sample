package demo.vicwang.myapplication.mvp.model.retrofit

import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainBridge
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitApiRepository : RxMainBridge.Model {
    private val url = "https://data.taipei/opendata/datalist/"

    private var retrofitApi: RetrofitApiInterface

    private fun getBuildOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(getBuildOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        this.retrofitApi = retrofit.create<RetrofitApiInterface>(RetrofitApiInterface::class.java)
    }


    override fun getHouseData(): Observable<ResponseItem> {
        return retrofitApi.getHouseData()
    }

    override fun getAnimalData(targetArea: String): Observable<ResponseItem> {
        return retrofitApi.getAnimalData(targetArea)
    }

}
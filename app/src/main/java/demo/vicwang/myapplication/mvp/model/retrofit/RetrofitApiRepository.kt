package demo.vicwang.myapplication.mvp.model.retrofit

import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainBridge
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitApiRepository : RxMainBridge.Model {
    private val url = "https://data.taipei/opendata/datalist/"

    private var retrofitApi: RetrofitApiInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
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
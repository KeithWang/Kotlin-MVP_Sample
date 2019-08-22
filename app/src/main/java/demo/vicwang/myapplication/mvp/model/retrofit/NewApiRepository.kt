package demo.vicwang.myapplication.mvp.model.retrofit

import com.google.gson.JsonObject
import demo.vicwang.myapplication.mvp.presenter.rxjava.NewMainBridge
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class NewApiRepository : NewMainBridge.Model {
    private val url = "https://data.taipei/opendata/datalist/"

    private var api: ApiInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create<ApiInterface>(ApiInterface::class.java)
    }


    override fun getHouseData(): Observable<JsonObject> {
        return api.getHouseData()
    }

    override fun getAnimalData(targetArea: String): Observable<JsonObject> {
        return api.getAnimalData(targetArea)
    }

}
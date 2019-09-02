package demo.vicwang.myapplication.mvp.model.retrofit

import demo.vicwang.myapplication.mvp.model.LoginResponseItem
import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainBridge
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitApiRepository : RxMainBridge.Model {

    /*
    * Taipei Zoo
    * */
    private val urlTaipei = "https://data.taipei/opendata/datalist/"
    private var apiTaipei: RetrofitApiInterface

    /*
    * Login Test
    * */
    private val urlLogin = "https://reqres.in/"
    private var apiLogin: ApiLoginInterface

    private fun getBuildOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(urlTaipei)
                .client(getBuildOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        this.apiTaipei = retrofit.create<RetrofitApiInterface>(RetrofitApiInterface::class.java)

        val retrofitLogin = Retrofit.Builder()
                .baseUrl(urlLogin)
                .client(getBuildOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        this.apiLogin = retrofitLogin.create<ApiLoginInterface>(ApiLoginInterface::class.java)
    }

    override fun getToken(email: String, password: String): Observable<LoginResponseItem> {
        return apiLogin.getToken(email, password)
    }

    override fun getHouseData(token: String): Observable<ResponseItem> {
        return apiTaipei.getHouseData(token)
    }

    override fun getAnimalData(targetArea: String): Observable<ResponseItem> {
        return apiTaipei.getAnimalData(targetArea)
    }

}
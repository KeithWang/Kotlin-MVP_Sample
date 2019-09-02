package demo.vicwang.myapplication.mvp.model.retrofit

import demo.vicwang.myapplication.mvp.model.LoginResponseItem
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiLoginInterface {

    /*
    * Free Api Test
    * https://reqres.in/
    * */
    @POST("/api/login")
    @FormUrlEncoded
    fun getToken(@Field("email") email: String,
                 @Field("password") password: String): Observable<LoginResponseItem>
}
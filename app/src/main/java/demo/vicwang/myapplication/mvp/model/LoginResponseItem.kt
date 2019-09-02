package demo.vicwang.myapplication.mvp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseItem(
        @SerializedName("token") val token: String
) : Serializable
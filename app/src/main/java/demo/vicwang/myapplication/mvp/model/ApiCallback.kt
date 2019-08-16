package demo.vicwang.myapplication.mvp.model

interface ApiCallback {
    fun onSuccess(resultMsg: String)
    fun onFailed(errorMsg: String, e: Exception)
}
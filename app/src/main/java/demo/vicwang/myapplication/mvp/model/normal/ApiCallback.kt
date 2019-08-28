package demo.vicwang.myapplication.mvp.model.normal

interface ApiCallback {
    fun onSuccess(resultMsg: String)
    fun onFailed(errorMsg: String, e: Exception)
}
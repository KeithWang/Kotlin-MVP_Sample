package demo.vicwang.myapplication.mvp.presenter.normal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.model.normal.ApiCallback
import demo.vicwang.myapplication.mvp.model.normal.ApiRepository
import java.util.*

class MainPresenter(view: MainBridge.View, repo: ApiRepository) : MainBridge.Presenter {

    private val mView: MainBridge.View = view
    private val mApiRepo: ApiRepository = repo

    override fun initHouseData() {
        mView.onShowLoadingView(true)
        mApiRepo.getHouseData(object : ApiCallback {
            override fun onSuccess(resultMsg: String) {
                try {
                    val array = Gson().fromJson(resultMsg, ResponseItem::class.java).resultJsonArray

                    val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
                    val listData: ArrayList<MainHouseListItem> = Gson().fromJson(array.toString(), listType)

                    mView.onHouseDataLoadSuccess(array.toString(), listData)
                    mView.onShowLoadingView(false)

                } catch (e: Exception) {
                    mView.onShowErrorMsg(1)
                    mView.onShowLoadingView(false)
                }

            }

            override fun onFailed(errorMsg: String, e: Exception) {
                mView.onShowErrorMsg(1)
                mView.onShowLoadingView(false)
            }
        })
    }

    override fun initAnimalData(item: MainHouseListItem) {
        mView.onShowLoadingView(true)
        mApiRepo.getAnimalData(item.E_Name, object : ApiCallback {
            override fun onSuccess(resultMsg: String) {
                try {
                    val array = Gson().fromJson(resultMsg, ResponseItem::class.java).resultJsonArray
                    val listType = object : TypeToken<ArrayList<HouseListAnimalItem>>() {}.type
                    val listData: ArrayList<HouseListAnimalItem> = Gson().fromJson(array.toString(), listType)

                    mView.onAnimalDataLoadSuccess(array.toString(), listData, item)
                    mView.onShowLoadingView(false)
                } catch (e: Exception) {
                    mView.onShowErrorMsg(1)
                    mView.onShowLoadingView(false)
                }

            }

            override fun onFailed(errorMsg: String, e: Exception) {
                mView.onShowErrorMsg(0)
                mView.onShowLoadingView(false)
            }
        })
    }
}
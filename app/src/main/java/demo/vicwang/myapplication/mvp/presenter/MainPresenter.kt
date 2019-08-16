package demo.vicwang.myapplication.mvp.presenter

import com.google.gson.Gson
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.ApiCallback
import demo.vicwang.myapplication.mvp.model.ApiRepository
import org.json.JSONObject
import java.util.*

class MainPresenter(view: MainBridge.View, repo: ApiRepository) : MainBridge.Presenter {

    private val mView: MainBridge.View = view
    private val mApiRepo: ApiRepository = repo

    override fun initHouseData() {
        mApiRepo.getHouseData(object : ApiCallback {
            override fun onSuccess(resultMsg: String) {
                try {
                    val array = JSONObject(resultMsg).getJSONObject("result").getJSONArray("results")
                    val listData = ArrayList<MainHouseListItem>()
                    val gson = Gson()
                    for (i in 0 until array.length()) {
                        val data = gson.fromJson(array.get(i).toString(), MainHouseListItem::class.java)
                        listData.add(data)
                    }

                    mView.onHouseDataLoadSuccess(array.toString(), listData)

                } catch (e: Exception) {
                    mView.onShowErrorMsg(1)
                }

            }

            override fun onFailed(errorMsg: String, e: Exception) {
                mView.onShowErrorMsg(1)
            }
        })
    }

    override fun initAnimalData(item: MainHouseListItem) {
        mApiRepo.getAnimalData(item.E_Name, object : ApiCallback {
            override fun onSuccess(resultMsg: String) {
                try {
                    val array = JSONObject(resultMsg).getJSONObject("result").getJSONArray("results")
                    val listData = ArrayList<HouseListAnimalItem>()
                    val gson = Gson()
                    for (i in 0 until array.length()) {
                        val data = gson.fromJson(array.get(i).toString(), HouseListAnimalItem::class.java)
                        listData.add(data)
                    }
                    mView.onAnimalDataLoadSuccess(array.toString(), listData, item)
                } catch (e: Exception) {
                    mView.onShowErrorMsg(1)
                }

            }

            override fun onFailed(errorMsg: String, e: Exception) {
                mView.onShowErrorMsg(0)
            }
        })
    }
}
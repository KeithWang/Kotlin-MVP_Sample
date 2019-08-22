package demo.vicwang.myapplication.mvp.presenter.rxjava

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.retrofit.NewApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class NewMainPresenter(view: NewMainBridge.View, repo: NewApiRepository) : NewMainBridge.Presenter {

    private val mView: NewMainBridge.View = view
    private val mApiRepo: NewApiRepository = repo

    override fun initHouseData() {

        mApiRepo.getHouseData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val array = result.getAsJsonObject("result").getAsJsonArray("results")
                    val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
                    val listData: ArrayList<MainHouseListItem> = Gson().fromJson(array.toString(), listType)

                    mView.onHouseDataLoadSuccess(array.toString(), listData)
                }, {
                    mView.onShowErrorMsg(1)
                })
    }

    override fun initAnimalData(item: MainHouseListItem) {
        mApiRepo.getAnimalData(item.E_Name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val array = result.getAsJsonObject("result").getAsJsonArray("results")
                    val listType = object : TypeToken<ArrayList<HouseListAnimalItem>>() {}.type
                    val listData: ArrayList<HouseListAnimalItem> = Gson().fromJson(array.toString(), listType)

                    mView.onAnimalDataLoadSuccess(array.toString(), listData, item)
                    Log.d("Kotlin", result.toString())
                }, { error ->
                    error.printStackTrace()
                    mView.onShowErrorMsg(1)
                })
    }
}
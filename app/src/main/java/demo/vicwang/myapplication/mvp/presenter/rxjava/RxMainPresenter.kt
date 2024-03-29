package demo.vicwang.myapplication.mvp.presenter.rxjava

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.retrofit.RetrofitApiRepository
import demo.vicwang.myapplication.mvp.presenter.rxjava.provider.SchedulerProvider
import java.util.*

class RxMainPresenter(private val mView: RxMainBridge.View
                      , private val mApiRepo: RetrofitApiRepository
                      , private val mRxProvider: SchedulerProvider) : RxMainBridge.Presenter {

    override fun onLogin(email: String, password: String) {
        mApiRepo.getToken(email, password)
                .subscribeOn(mRxProvider.io())
                .observeOn(mRxProvider.ui())
                .doOnSubscribe { mView.onShowLoadingView(true) }
                .doFinally {
                }
                .subscribe({ result ->
                    /*
                    * It used toke token to simulate login action.
                    * */
                    initHouseData(result.token)
                }, { error ->
                    mView.onShowErrorMsg(2)
                    mView.onShowLoadingView(false)
                    error.printStackTrace()
                })
    }

    override fun initHouseData(token: String) {
        mApiRepo.getHouseData(token)
                .subscribeOn(mRxProvider.io())
                .observeOn(mRxProvider.ui())
                .doOnSubscribe { mView.onShowLoadingView(true) }
                .doOnTerminate { mView.onShowLoadingView(false) }
                .subscribe({ result ->
                    val array = result.resultJsonArray
                    val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
                    val listData: ArrayList<MainHouseListItem> = Gson().fromJson(array.toString(), listType)

                    mView.onHouseDataLoadSuccess(array.toString(), listData)
                }, { error ->
                    error.printStackTrace()
                    mView.onShowErrorMsg(1)
                })
    }

    override fun initAnimalData(item: MainHouseListItem) {
        mApiRepo.getAnimalData(item.E_Name)
                .subscribeOn(mRxProvider.io())
                .observeOn(mRxProvider.ui())
                .doOnSubscribe { mView.onShowLoadingView(true) }
                .doOnTerminate { mView.onShowLoadingView(false) }
                .subscribe({ result ->
                    val array = result.resultJsonArray
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

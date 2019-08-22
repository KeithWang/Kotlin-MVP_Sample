package demo.vicwang.myapplication.mvp.presenter.rxjava

import com.google.gson.JsonObject
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.presenter.base.BaseModel
import demo.vicwang.myapplication.mvp.presenter.base.BasePresenter
import demo.vicwang.myapplication.mvp.presenter.base.BaseView
import io.reactivex.Observable

interface NewMainBridge {
    interface View : BaseView<Presenter> {
        fun onShowLoadingView(isShow: Boolean)

        fun onShowErrorMsg(errorType: Int)

        fun onHouseDataLoadSuccess(result: String, o: Any)

        fun onAnimalDataLoadSuccess(result: String, o: Any, item: MainHouseListItem)
    }

    interface Presenter : BasePresenter<Model> {
        fun initHouseData()

        fun initAnimalData(item: MainHouseListItem)
    }

    interface Model : BaseModel {

        fun getHouseData(): Observable<JsonObject>

        fun getAnimalData(targetArea: String): Observable<JsonObject>
    }

}
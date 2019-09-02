package demo.vicwang.myapplication.mvp.presenter.rxjava

import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.LoginResponseItem
import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.presenter.base.BaseModel
import demo.vicwang.myapplication.mvp.presenter.base.BasePresenter
import demo.vicwang.myapplication.mvp.presenter.base.BaseView
import io.reactivex.Observable

interface RxMainBridge {
    interface View : BaseView<Presenter> {
        fun onShowLoadingView(isShow: Boolean)

        fun onShowErrorMsg(errorType: Int)

        fun onHouseDataLoadSuccess(result: String, o: Any)

        fun onAnimalDataLoadSuccess(result: String, o: Any, item: MainHouseListItem)
    }

    interface Presenter : BasePresenter<Model> {
        fun onLogin(email: String, password: String)

        fun initHouseData(token: String)

        fun initAnimalData(item: MainHouseListItem)
    }

    interface Model : BaseModel {
        fun getToken(email: String, password: String): Observable<LoginResponseItem>

        fun getHouseData(token: String): Observable<ResponseItem>

        fun getAnimalData(targetArea: String): Observable<ResponseItem>
    }

}
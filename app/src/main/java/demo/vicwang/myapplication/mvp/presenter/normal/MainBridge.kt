package demo.vicwang.myapplication.mvp.presenter.normal

import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.normal.ApiCallback
import demo.vicwang.myapplication.mvp.presenter.base.BaseModel
import demo.vicwang.myapplication.mvp.presenter.base.BasePresenter
import demo.vicwang.myapplication.mvp.presenter.base.BaseView

interface MainBridge {
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
        fun getToken(email: String, password: String, callback: ApiCallback)

        fun getHouseData(token: String, callback: ApiCallback)

        fun getAnimalData(targetArea: String, callback: ApiCallback)
    }

}
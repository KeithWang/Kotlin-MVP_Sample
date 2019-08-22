package demo.vicwang.myapplication.modul

import demo.vicwang.myapplication.mvp.model.ApiRepository
import demo.vicwang.myapplication.mvp.model.retrofit.NewApiRepository
import demo.vicwang.myapplication.mvp.presenter.MainBridge
import demo.vicwang.myapplication.mvp.presenter.MainPresenter
import demo.vicwang.myapplication.mvp.presenter.rxjava.NewMainBridge
import demo.vicwang.myapplication.mvp.presenter.rxjava.NewMainPresenter
import org.koin.dsl.module

val ApplicationModule = module {

    factory { (view: MainBridge.View) -> MainPresenter(view, ApiRepository()) }

    factory { (view: NewMainBridge.View) -> NewMainPresenter(view, NewApiRepository()) }
}
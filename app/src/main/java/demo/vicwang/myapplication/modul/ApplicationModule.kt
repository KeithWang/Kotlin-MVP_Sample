package demo.vicwang.myapplication.modul

import demo.vicwang.myapplication.mvp.model.ApiRepository
import demo.vicwang.myapplication.mvp.presenter.MainBridge
import demo.vicwang.myapplication.mvp.presenter.MainPresenter
import org.koin.dsl.module

val ApplicationModule = module {

    factory { (view: MainBridge.View) -> MainPresenter(view, ApiRepository()) }
}
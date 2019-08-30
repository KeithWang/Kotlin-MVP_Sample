package demo.vicwang.myapplication.modul

import demo.vicwang.myapplication.mvp.model.normal.ApiRepository
import demo.vicwang.myapplication.mvp.model.retrofit.RetrofitApiRepository
import demo.vicwang.myapplication.mvp.presenter.normal.MainBridge
import demo.vicwang.myapplication.mvp.presenter.normal.MainPresenter
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainBridge
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainPresenter
import demo.vicwang.myapplication.mvp.presenter.rxjava.provider.AppSchedulerProvider
import demo.vicwang.myapplication.mvp.presenter.rxjava.provider.SchedulerProvider
import org.koin.dsl.module

val ApplicationModule = module {

    single { ApiRepository() }

    factory { (view: MainBridge.View) -> MainPresenter(view, get()) }

    single<SchedulerProvider> { AppSchedulerProvider() }

    single { RetrofitApiRepository() }

    factory { (view: RxMainBridge.View) -> RxMainPresenter(view, get(), get()) }
}
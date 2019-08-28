package demo.vicwang.myapplication.mvp.presenter.rxjava.provider

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler
}
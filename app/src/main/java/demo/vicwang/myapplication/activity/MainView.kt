package demo.vicwang.myapplication.activity

import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem

interface MainView {
    fun onCallToast(str: String, Long: Boolean)

    fun onBackFragment()

    fun onOpenAnimalInfoPage(item: HouseListAnimalItem)
}
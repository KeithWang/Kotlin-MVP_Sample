package demo.vicwang.myapplication.mvp.presenter.rxjava

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.model.retrofit.RetrofitApiRepository
import demo.vicwang.myapplication.mvp.presenter.rxjava.provider.TrampolineSchedulerProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.*


class RxMainPresenterTest {

    private lateinit var presenter: RxMainPresenter

    private val view: RxMainBridge.View = Mockito.mock(RxMainBridge.View::class.java)
    private val rope: RetrofitApiRepository = Mockito.mock(RetrofitApiRepository::class.java)
    private val rxProvider: TrampolineSchedulerProvider = TrampolineSchedulerProvider()

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun setup() {
        presenter = RxMainPresenter(view, rope, rxProvider)
    }

    @Test
    fun `Init House Data Has Http Failed or Observable Is Null`() {
        val expectErrorType = 1

        `when`(rope.getHouseData()).thenReturn(Observable.error(NullPointerException()))

        presenter.initHouseData()

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success But Json Inner Error`() {
        val successJsonStr = "{}"
        val expectErrorType = 1

        val item = Gson().fromJson(successJsonStr, ResponseItem::class.java)

        `when`(rope.getHouseData()).thenReturn(Observable.just(item))

        presenter.initHouseData()

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success`() {
        val successJsonStr = "{\"result\":{\"results\":[{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"},{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}]}}"

        val item = Gson().fromJson(successJsonStr, ResponseItem::class.java)

        `when`(rope.getHouseData()).thenReturn(Observable.just(item))

        presenter.initHouseData()

        val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
        val listData: ArrayList<MainHouseListItem> = Gson().fromJson(item.resultJsonArray.toString(), listType)

        verify(view).onHouseDataLoadSuccess(item.resultJsonArray.toString(), listData)
    }

    @Test
    fun `Init Animal Data Has Http Failed or Observable Is Null`() {
        val expectErrorType = 1
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        `when`(rope.getAnimalData(ArgumentMatchers.anyString())).thenReturn(Observable.error(NullPointerException()))

        presenter.initAnimalData(houseItem)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init Animal Data Has Http Success But Json Inner Error`() {
        val successJsonStr = "{}"
        val expectErrorType = 1
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        val item = Gson().fromJson(successJsonStr, ResponseItem::class.java)

        `when`(rope.getAnimalData(ArgumentMatchers.anyString())).thenReturn(Observable.just(item))

        presenter.initAnimalData(houseItem)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init Animal Data Has Http Success`() {
        val successJsonStr = "{\"result\":{\"results\":[{\"_full_count\":\"1\",\"A_Behavior\":\"\",\"A_Voice03_URL\":\"\",\"A_POIGroup\":\"\",\"rank\":0.0573088,\"A_Code\":\"Koala\",\"A_Pic04_ALT\":\"無尾熊\",\"A_Voice03_ALT\":\"\",\"A_Theme_URL\":\"\",\"A_Summary\":\"\",\"A_Update\":\"2015\\/11\\/23\",\"A_Pic02_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic02.jpg\",\"A_pdf01_ALT\":\"\",\"\uFEFFA_Name_Ch\":\"無尾熊\",\"A_Theme_Name\":\"\",\"A_pdf02_ALT\":\"\",\"A_Family\":\"無尾熊科\",\"A_Adopt\":\"\",\"A_Voice01_ALT\":\"\",\"A_Pic02_ALT\":\"無尾熊\",\"A_Vedio_URL\":\"\",\"A_Pic04_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic04.jpg\",\"A_Order\":\"有袋目\",\"A_pdf01_URL\":\"\",\"A_Voice02_ALT\":\"\",\"A_Diet\":\"\",\"A_Name_Latin\":\"Phascolarctos cinereus\",\"A_Feature\":\"\",\"A_Habitat\":\"桉樹林中。\",\"A_Phylum\":\"脊索動物門\",\"A_Class\":\"哺乳綱\",\"A_Pic03_ALT\":\"無尾熊\",\"A_AlsoKnown\":\"\",\"A_Voice02_URL\":\"\",\"A_Name_En\":\"Koala\",\"A_Pic03_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic03.jpg\",\"A_Interpretation\":\"\",\"A_Location\":\"無尾熊館\",\"A_Voice01_URL\":\"\",\"A_pdf02_URL\":\"\",\"A_CID\":\"230\",\"A_Keywords\":\"\",\"A_Pic01_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic01.jpg\",\"A_Conservation\":\"\",\"A_Pic01_ALT\":\"無尾熊\",\"_id\":306,\"A_Geo\":\"MULTIPOINT ((121.5823688 24.9983738), (121.5824439 24.998155))\",\"A_Crisis\":\"\"}]}}"
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        val item = Gson().fromJson(successJsonStr, ResponseItem::class.java)


        `when`(rope.getAnimalData(ArgumentMatchers.anyString())).thenReturn(Observable.just(item))

        presenter.initAnimalData(houseItem)

        val listType = object : TypeToken<ArrayList<HouseListAnimalItem>>() {}.type
        val listData: ArrayList<HouseListAnimalItem> = Gson().fromJson(item.resultJsonArray.toString(), listType)

        verify(view).onAnimalDataLoadSuccess(item.resultJsonArray.toString(), listData, houseItem)
    }
}
package demo.vicwang.myapplication.mvp.presenter

import com.google.gson.Gson
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.ApiCallback
import demo.vicwang.myapplication.mvp.model.ApiRepository
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*


class MainPresenterTest {

    private lateinit var presenter: MainPresenter

    private val view: MainBridge.View = mock(MainBridge.View::class.java)
    private val rope: ApiRepository = mock(ApiRepository::class.java)

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun setup() {
        presenter = MainPresenter(view, rope)
    }

    @Test
    fun `Init House Data Has Http Failed`() {
        val failString = "fail"
        val expectErrorType = 1

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[0] as ApiCallback
            callback.onFailed(failString, Exception())
        }.`when`(rope).getHouseData(any(ApiCallback::class.java))

        presenter.initHouseData()

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success But Json Error`() {
        val successJsonStr = ""
        val expectErrorType = 1

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[0] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getHouseData(any(ApiCallback::class.java))

        presenter.initHouseData()

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success`() {
        val successJsonStr = "{\"result\":{\"results\":[{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"},{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}]}}"

        val successArray = JSONObject(successJsonStr).getJSONObject("result").getJSONArray("results")
        val successListData = ArrayList<MainHouseListItem>()
        for (i in 0 until successArray.length()) {
            val data = Gson().fromJson(successArray.get(i).toString(), MainHouseListItem::class.java)
            successListData.add(data)
        }

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[0] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getHouseData(any(ApiCallback::class.java))

        presenter.initHouseData()

        verify(view).onHouseDataLoadSuccess(successArray.toString(), successListData)
    }

    @Test
    fun `Init Animal Data Has Http Failed`() {
        val failString = "fail"
        val expectErrorType = 0
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onFailed(failString, Exception())
        }.`when`(rope).getAnimalData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initAnimalData(houseItem)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init Animal Data Has Http Success But Json Error`() {
        val successJsonStr = ""
        val expectErrorType = 1
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getAnimalData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initAnimalData(houseItem)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init Animal Data Has Http Success`() {
        val successJsonStr = "{\"result\":{\"results\":[{\"_full_count\":\"1\",\"A_Behavior\":\"\",\"A_Voice03_URL\":\"\",\"A_POIGroup\":\"\",\"rank\":0.0573088,\"A_Code\":\"Koala\",\"A_Pic04_ALT\":\"無尾熊\",\"A_Voice03_ALT\":\"\",\"A_Theme_URL\":\"\",\"A_Summary\":\"\",\"A_Update\":\"2015\\/11\\/23\",\"A_Pic02_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic02.jpg\",\"A_pdf01_ALT\":\"\",\"\uFEFFA_Name_Ch\":\"無尾熊\",\"A_Theme_Name\":\"\",\"A_pdf02_ALT\":\"\",\"A_Family\":\"無尾熊科\",\"A_Adopt\":\"\",\"A_Voice01_ALT\":\"\",\"A_Pic02_ALT\":\"無尾熊\",\"A_Vedio_URL\":\"\",\"A_Pic04_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic04.jpg\",\"A_Order\":\"有袋目\",\"A_pdf01_URL\":\"\",\"A_Voice02_ALT\":\"\",\"A_Diet\":\"\",\"A_Name_Latin\":\"Phascolarctos cinereus\",\"A_Feature\":\"\",\"A_Habitat\":\"桉樹林中。\",\"A_Phylum\":\"脊索動物門\",\"A_Class\":\"哺乳綱\",\"A_Pic03_ALT\":\"無尾熊\",\"A_AlsoKnown\":\"\",\"A_Voice02_URL\":\"\",\"A_Name_En\":\"Koala\",\"A_Pic03_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic03.jpg\",\"A_Interpretation\":\"\",\"A_Location\":\"無尾熊館\",\"A_Voice01_URL\":\"\",\"A_pdf02_URL\":\"\",\"A_CID\":\"230\",\"A_Keywords\":\"\",\"A_Pic01_URL\":\"http:\\/\\/www.zoo.gov.tw\\/iTAP\\/03_Animals\\/KoalaHouse\\/Koala\\/Koala_Pic01.jpg\",\"A_Conservation\":\"\",\"A_Pic01_ALT\":\"無尾熊\",\"_id\":306,\"A_Geo\":\"MULTIPOINT ((121.5823688 24.9983738), (121.5824439 24.998155))\",\"A_Crisis\":\"\"}]}}"
        val houseItemStr = "{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}"
        val houseItem = Gson().fromJson(houseItemStr, MainHouseListItem::class.java)

        val ansArray = JSONObject(successJsonStr).getJSONObject("result").getJSONArray("results")
        val ansListData = ArrayList<HouseListAnimalItem>()
        for (i in 0 until ansArray.length()) {
            val data = Gson().fromJson(ansArray.get(i).toString(), HouseListAnimalItem::class.java)
            ansListData.add(data)
        }

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getAnimalData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initAnimalData(houseItem)

        verify(view).onAnimalDataLoadSuccess(ansArray.toString(), ansListData, houseItem)
    }


}

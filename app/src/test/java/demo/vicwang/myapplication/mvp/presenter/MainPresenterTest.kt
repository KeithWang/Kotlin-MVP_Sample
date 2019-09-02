package demo.vicwang.myapplication.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.mvp.model.ResponseItem
import demo.vicwang.myapplication.mvp.model.normal.ApiCallback
import demo.vicwang.myapplication.mvp.model.normal.ApiRepository
import demo.vicwang.myapplication.mvp.presenter.normal.MainBridge
import demo.vicwang.myapplication.mvp.presenter.normal.MainPresenter
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
    fun `Login Fail`() {
        val account = "account"
        val password = "password"
        val failString = "fail"
        val expectErrorType = 2

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[2] as ApiCallback
            callback.onFailed(failString, Exception())
        }.`when`(rope).getToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.onLogin(account, password)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Login Success And Get Data Success`() {
        val account = "account"
        val password = "password"
        val resultStr = "{\"token\":\"token\"}"

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[2] as ApiCallback
            callback.onSuccess(resultStr)
        }.`when`(rope).getToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        /*
        * login success
        * */
        val successJsonStr = "{\"result\":{\"results\":[{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"},{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}]}}"

        val successArray = Gson().fromJson(successJsonStr, ResponseItem::class.java).resultJsonArray

        val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
        val successListData: ArrayList<MainHouseListItem> = Gson().fromJson(successArray.toString(), listType)

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getHouseData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.onLogin(account, password)


        verify(view).onHouseDataLoadSuccess(successArray.toString(), successListData)
    }

    @Test
    fun `Init House Data Has Http Failed`() {
        val temToken = "token"
        val failString = "fail"
        val expectErrorType = 1

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onFailed(failString, Exception())
        }.`when`(rope).getHouseData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initHouseData(temToken)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success But Json Error`() {
        val temToken = "token"
        val successJsonStr = ""
        val expectErrorType = 1

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getHouseData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initHouseData(temToken)

        verify(view).onShowErrorMsg(expectErrorType)
    }

    @Test
    fun `Init House Data Has Http Success`() {
        val temToken = "token"
        val successJsonStr = "{\"result\":{\"results\":[{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"},{\"E_Pic_URL\":\"\",\"E_Geo\":\"\",\"E_Info\":\"\",\"E_no\":\"1\",\"E_Category\":\"\",\"E_Name\":\"\",\"E_Memo\":\"\",\"_id\":1,\"E_URL\":\"\"}]}}"

        val successArray = Gson().fromJson(successJsonStr, ResponseItem::class.java).resultJsonArray

        val listType = object : TypeToken<ArrayList<MainHouseListItem>>() {}.type
        val successListData: ArrayList<MainHouseListItem> = Gson().fromJson(successArray.toString(), listType)

        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getHouseData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initHouseData(temToken)

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

        val ansArray = Gson().fromJson(successJsonStr, ResponseItem::class.java).resultJsonArray

        val listType = object : TypeToken<ArrayList<HouseListAnimalItem>>() {}.type
        val ansListData: ArrayList<HouseListAnimalItem> = Gson().fromJson(ansArray.toString(), listType)


        doAnswer {
            val arguments = it.arguments
            val callback = arguments[1] as ApiCallback
            callback.onSuccess(successJsonStr)
        }.`when`(rope).getAnimalData(ArgumentMatchers.anyString(), any(ApiCallback::class.java))

        presenter.initAnimalData(houseItem)

        verify(view).onAnimalDataLoadSuccess(ansArray.toString(), ansListData, houseItem)
    }


}

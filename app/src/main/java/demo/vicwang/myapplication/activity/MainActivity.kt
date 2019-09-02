package demo.vicwang.myapplication.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import demo.vicwang.myapplication.R
import demo.vicwang.myapplication.adapter.MainHouseListAdapter
import demo.vicwang.myapplication.adapter.item.HouseListAnimalItem
import demo.vicwang.myapplication.adapter.item.MainHouseListItem
import demo.vicwang.myapplication.fragment.AnimalInfoFragment
import demo.vicwang.myapplication.fragment.HouseInfoFragment
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainBridge
import demo.vicwang.myapplication.mvp.presenter.rxjava.RxMainPresenter
import demo.vicwang.myapplication.utility.ViewClick
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class MainActivity : BasicActivity(), RxMainBridge.View, MainView {
//class MainActivity : BasicActivity(), MainBridge.View, MainView {

    /*
     * Tool or page widget
     * */
    private lateinit var wRecycleView: RecyclerView
    private lateinit var wLay_Loading_Area: FrameLayout
    private lateinit var mDialog: Dialog
    /*
     * Toolbar
     * */
    private lateinit var wTxt_Toolbar_Title: TextView
    private lateinit var wImg_Toolbar_Home: ImageView
    private lateinit var wLay_Btn_Home: FrameLayout

    /*
     * Presenter
     * */
    private val mPresenter: RxMainPresenter by inject { parametersOf(this) }
//    private val mPresenter: MainPresenter by inject { parametersOf(this) }

    /*
     * Main Page data
     * */
    private var mData = ArrayList<MainHouseListItem>()

    private lateinit var mAreaSelectItem: MainHouseListItem

    private var mIsHome = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInit()
        setViewValue()
        setViewListener()
    }


    private fun viewInit() {

        wRecycleView = findViewById(R.id.main_recycleview)

        wTxt_Toolbar_Title = findViewById(R.id.main_toolsbar_txt_title)
        wImg_Toolbar_Home = findViewById(R.id.main_toolsbar_img_home)
        wLay_Btn_Home = findViewById(R.id.main_toolsbar_lay_btn_home_click)

        wLay_Loading_Area = findViewById(R.id.main_lay_loading_area)
    }

    private fun setViewValue() {
        setTitleBar("", true, false)

        opeLoginDialog()
    }

    private fun setViewListener() {
        wLay_Btn_Home.setOnClickListener(mNormalClickListener)
    }

    /*
     * Custom Title Bar
     * */
    private fun setTitleBar(title: String, menu: Boolean, ignoreAnim: Boolean) {
        mIsHome = menu
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val drawable: AnimatedVectorDrawable
        if (menu) {
            drawable = getDrawable(R.drawable.ic_back_animatable) as AnimatedVectorDrawable
        } else {
            drawable = getDrawable(R.drawable.ic_menu_animatable) as AnimatedVectorDrawable
        }
        if (!ignoreAnim)
            wImg_Toolbar_Home.setImageDrawable(drawable)
        drawable.start()

        wTxt_Toolbar_Title.text = if (title == "") getString(R.string.main_taipei_zoo) else title
    }

    /*
     * Add Fragment in main_fragment_outer_page_container
     * */
    private fun onOpenFragment(f: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.fragment_anim_slide_right_in, R.animator.fragment_anim_slide_do_nothing, R.animator.fragment_anim_slide_do_nothing, R.animator.fragment_anim_slide_right_out)
                .add(R.id.main_fragment_outer_page_container, f, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit()
    }

    /*
     * Normal Click
     * */
    private val mNormalClickListener = object : ViewClick() {
        override fun CustomOnClick(view: View) {
            when (view.id) {
                R.id.main_toolsbar_lay_btn_home_click -> {
                    if (mIsHome)
                        onCallToast(":)", false)
                    else
                        onBackPressed()
                }
            }
        }
    }

    /*
     * Presenter View Area
     * */
    override fun onShowLoadingView(isShow: Boolean) {
        runOnUiThread {
            if (isShow)
                wLay_Loading_Area.visibility = View.VISIBLE
            else
                wLay_Loading_Area.visibility = View.INVISIBLE
        }
    }

    override fun onShowErrorMsg(errorType: Int) {
        runOnUiThread {
            if (errorType == 2)
                openErrorDialog(getString(R.string.http_login_fail), errorType)
            else
                openErrorDialog(getString(R.string.http_get_error), errorType)
        }
    }

    override fun onHouseDataLoadSuccess(result: String, o: Any) {
        runOnUiThread {
            loadListItem(o as ArrayList<MainHouseListItem>)
        }
    }

    override fun onAnimalDataLoadSuccess(result: String, o: Any, item: MainHouseListItem) {
        runOnUiThread {
            setTitleBar(item.E_Name, false, false)

            onOpenFragment(
                    HouseInfoFragment.newInstance(item, o as ArrayList<HouseListAnimalItem>)
                    , HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG)
        }
    }


    /*
     * Loading Main page List Item fun
     * */
    private fun loadListItem(data: ArrayList<MainHouseListItem>) {
        mData.clear()
        mData = data
        wRecycleView.layoutManager = LinearLayoutManager(mContext)
        val mAdapter = MainHouseListAdapter(mContext, mData)
        mAdapter.setClickListener(recycleClick)
        wRecycleView.adapter = mAdapter
        wRecycleView.itemAnimator = DefaultItemAnimator()
    }

    /*
     * Main Page list click listener
     * */
    private val recycleClick = object : MainHouseListAdapter.ItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            mAreaSelectItem = mData[position]
            mPresenter.initAnimalData(mData[position])
        }
    }

    /*
     * Back Pressed fun
     * */
    override fun onBackPressed() {
        onBackFragment()
    }

    /*
     * Fragment Callback Area
     * */
    override fun onCallToast(str: String, Long: Boolean) {
        showToast(str, Long)
    }

    override fun onBackFragment() {
        val fHouse = supportFragmentManager.findFragmentByTag(HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG)
        val fAnimalInfo = supportFragmentManager.findFragmentByTag(AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG)
        when {
            fAnimalInfo != null -> {
                setTitleBar(mAreaSelectItem.E_Name, false, true)
                supportFragmentManager.popBackStack(AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG, 1)
            }
            fHouse != null -> {
                setTitleBar("", true, false)
                supportFragmentManager.popBackStack(HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG, 1)
            }
            else -> finish()
        }
    }

    override fun onOpenAnimalInfoPage(item: HouseListAnimalItem) {
        setTitleBar(item.A_Name_Ch, false, true)
        onOpenFragment(AnimalInfoFragment.newInstance(item), AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG)
    }

    /*
     * Custom Login dialog
     * */
    private fun opeLoginDialog() {
        mDialog = Dialog(mContext, R.style.PauseDialog)
        mDialog.setContentView(R.layout.cusomt_login_dailog)
        Objects.requireNonNull<Window>(mDialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCancelable(false)

        val confirm = mDialog.findViewById<Button>(R.id.custom_login_dialog_btn_confirm)
        val editAccount = mDialog.findViewById<TextView>(R.id.custom_login_dialog_edit_account)
        val editPassword = mDialog.findViewById<TextView>(R.id.custom_login_dialog_edit_pwd)

        val permission = View.OnClickListener { v ->
            when (v.id) {
                R.id.custom_login_dialog_btn_confirm -> {
                    mPresenter.onLogin(editAccount.text.toString(), editPassword.text.toString())
                    mDialog.dismiss()
                }
            }
        }
        confirm.setOnClickListener(permission)

        if (!this.isFinishing)
            mDialog.show()
    }
}

package com.devkanhaiya.bookreader.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.di.HasComponent
import com.devkanhaiya.bookreader.di.Injector
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.di.component.DaggerActivityComponent
import com.devkanhaiya.bookreader.ui.manager.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasComponent<ActivityComponent>, HasToolbar, Navigator {
    override val component: ActivityComponent
        get() = activityComponent
    @Inject
    lateinit var navigationFactory: FragmentNavigationFactory

    @Inject
    lateinit var activityStarter: ActivityStarter

    @Inject
    lateinit var appPreferences: AppPreferences

    //protected var toolbar: Toolbar? = null
    //protected var toolbarTitle: AppCompatTextView? = null
    internal var progressDialog: ProgressDialog? = null
    internal var alertDialog: AlertDialog? = null

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        activityComponent = DaggerActivityComponent.builder()
                .bindApplicationComponent(Injector.INSTANCE.applicationComponent)
                .bindActivity(this)
                .build()

        inject(activityComponent)



        setContentView(findContentView())
        bindViewWithViewBinding((findViewById<ViewGroup>(android.R.id.content)).getChildAt(0))

        //findViewById<>()
        /*if (toolbar != null)
            setSupportActionBar(toolbar)*/

        setUpAlertDialog()

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)

        super.onCreate(savedInstanceState)

    }


    private fun setUpAlertDialog() {
        alertDialog = AlertDialog.Builder(this)
                .setPositiveButton("ok", null)
                .setTitle(R.string.app_name)
                .create()
    }

    fun <F : BaseFragment> getCurrentFragment(): F? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(findFragmentPlaceHolder()) as F
    }

    abstract fun findFragmentPlaceHolder(): Int

    @LayoutRes
    abstract fun findContentView(): Int


    abstract fun inject(activityComponent: ActivityComponent)

    abstract fun bindViewWithViewBinding(view: View)

    fun showErrorMessage(message: String?) {
        /*val f = getCurrentFragment<BaseFragment<*, *>>()
        if (f != null)
            Snackbar.make(f.getView()!!, message!!, BaseTransientBottomBar.LENGTH_SHORT).show()*/

    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun toggleLoader(show: Boolean) {

        if (show) {
            if (!progressDialog!!.isShowing)
                progressDialog!!.show()
        } else {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }
    }

    protected fun shouldGoBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        hideKeyboard()


        val currentFragment = getCurrentFragment<BaseFragment>()
        if (currentFragment == null)
            super.onBackPressed()
        else if (currentFragment.onBackActionPerform() && shouldGoBack())
            super.onBackPressed()

        // pending animation
        // overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

    }

    fun hideKeyboard() {
        // Check if no view has focus:

        val view = this.currentFocus
        if (view != null) {
            val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun showToolbar(b: Boolean) {
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {

            if (b)
                supportActionBar.show()
            else
                supportActionBar.hide()
        }
    }

    override fun setToolbarTitle(title: CharSequence) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun setToolbarTitle(@StringRes title: Int) {

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(title)
            //appToolbarTitle.setText(name);
        }
    }

    override fun showBackButton(b: Boolean) {

        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(b)
    }



    override fun setToolbarColor(@ColorRes color: Int) {


        /*if (toolbar != null) {
            toolbar.setBackgroundResource(color)
        }*/

    }


    override fun setToolbarElevation(isVisible: Boolean) {

        if (supportActionBar != null) {
            supportActionBar!!.elevation = if (isVisible) 8f else 0f
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    override fun <T : BaseFragment> load(tClass: Class<T>): FragmentActionPerformer<T> {
        return navigationFactory.make(tClass)
    }

    override fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder {
        return activityStarter.make(aClass)
    }

    override fun <T : BaseFragment> loadActivity(aClass: Class<out BaseActivity>, pageTClass: Class<T>): ActivityBuilder {
        return activityStarter.make(aClass).setPage(pageTClass)
    }


    override fun goBack() {
        onBackPressed()
    }


}

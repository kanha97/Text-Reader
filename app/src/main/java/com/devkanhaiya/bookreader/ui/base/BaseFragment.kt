package com.devkanhaiya.bookreader.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.di.HasComponent
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.di.module.FragmentModule
import com.devkanhaiya.bookreader.ui.manager.Navigator
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Created by hlink21 on 25/4/16.
 */
abstract class BaseFragment : Fragment(), HasComponent<FragmentComponent> {
    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    protected lateinit var toolbar: HasToolbar


    override val component: FragmentComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(FragmentModule(this))
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(createLayout(), container, false)
        bindViewWithViewBinding(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData()
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)!!
    }

    override fun onAttach(context: Context) {
        inject(component)
        super.onAttach(context)

        if (activity is HasToolbar)
            toolbar = activity as HasToolbar


    }


    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }

    fun showError(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
    }

    fun <T : BaseFragment> getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    open fun onShow() {

    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    override fun onDestroyView() {
        destroyViewBinding()
        super.onDestroyView()
    }

    public fun onError(throwable: Throwable) {
        Log.e(javaClass.simpleName, "Error From Base framework ", throwable)
    }

    protected abstract fun createLayout(): Int

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun bindViewWithViewBinding(view: View)

    protected abstract fun inject(fragmentComponent: FragmentComponent)
    protected abstract fun bindData()
    protected abstract fun destroyViewBinding()
}

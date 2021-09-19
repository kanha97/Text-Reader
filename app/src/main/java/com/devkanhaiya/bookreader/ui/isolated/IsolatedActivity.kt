package com.devkanhaiya.bookreader.ui.isolated

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.IsolatedActivityBinding
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.manager.ActivityStarter

class IsolatedActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: IsolatedActivityBinding

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun findContentView(): Int {
        return R.layout.isolated_activity
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = IsolatedActivityBinding.bind(view)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.imageViewButtonBack.setOnClickListener(this)

        window.statusBarColor = Color.WHITE

        if (savedInstanceState == null) {

            val page =
                intent.getSerializableExtra(ActivityStarter.ACTIVITY_FIRST_PAGE) as Class<BaseFragment>
            load(page)
                .setBundle(intent.extras!!)
                .add(false)
        }
    }

    fun showtoolBar(b: Boolean) {
        binding.layoutToolbar.visibility = if (b) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    fun showTitle(b: Boolean, text: String) {
        if (b) {
            binding.textViewTitle.visibility = View.VISIBLE
            binding.textViewTitle.text = text
        } else {
            binding.textViewTitle.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        goBack()
    }

}
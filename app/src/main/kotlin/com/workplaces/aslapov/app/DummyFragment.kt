package com.workplaces.aslapov.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class DummyFragment @Inject constructor() : BaseFragment(R.layout.fragment_main) {

    @Inject
    lateinit var mDummyViewModel: DummyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_main, container, false)
        val mainActivity = activity as MainActivity

        mDummyViewModel.mainFormState.observe(
            mainActivity,
            {
                state ->
                when (state) {
                    is MainSuccess -> mainActivity.onLogout()
                    is MainError -> Snackbar.make(view, state.error, Snackbar.LENGTH_LONG).show()
                }
            }
        )

        view.findViewById<Button>(R.id.logout).setOnClickListener {
            mDummyViewModel.logout()
        }
        return view
    }

    fun onLogout() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

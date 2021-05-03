package com.workplaces.aslapov.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class LoginFragment @Inject constructor() : BaseFragment(R.layout.login_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.signIn).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.login_to_sign_in_action)
        )
        view.findViewById<Button>(R.id.signUp).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.login_to_sign_up_action)
        )
    }
}

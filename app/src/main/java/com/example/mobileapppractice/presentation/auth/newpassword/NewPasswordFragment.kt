package com.example.mobileapppractice.presentation.auth.newpassword

/**
 * Фрагмент создания нового пароля
 *
 * @author Баданин
 * @since 2026-03-02
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
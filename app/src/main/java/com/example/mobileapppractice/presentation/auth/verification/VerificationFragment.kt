package com.example.mobileapppractice.presentation.auth.verification

/**
 * Фрагмент верификации OTP кода
 *
 * @author Баданин
 * @since 2026-03-02
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentVerificationBinding

class VerificationFragment : BaseFragment<FragmentVerificationBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVerificationBinding {
        return FragmentVerificationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
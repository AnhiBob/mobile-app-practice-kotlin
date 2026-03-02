package com.example.mobileapppractice.presentation.auth.forgot

/**
 * Фрагмент восстановления пароля
 * Отправляет email для сброса пароля
 *
 * @author Баданин
 * @since 2026-03-02
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapppractice.R
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentForgotBinding
import java.util.regex.Pattern

class ForgotFragment : BaseFragment<FragmentForgotBinding>() {

    private val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentForgotBinding {
        return FragmentForgotBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        // Кнопка назад - переход на Sign In
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
        }

        // Кнопка отправки
        binding.sendButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()

            if (validateEmail(email)) {
                sendResetCode(email)
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                showErrorDialog(getString(R.string.error_email_required))
                false
            }
            !emailPattern.matcher(email).matches() -> {
                showErrorDialog(getString(R.string.error_invalid_email))
                false
            }
            else -> true
        }
    }

    private fun sendResetCode(email: String) {
        showLoading()

        // Имитация запроса к серверу
        binding.root.postDelayed({
            hideLoading()

            // Показываем диалог как в макете
            showSuccessDialog()

        }, 1500)
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Успешно")
            .setMessage(R.string.success_email_sent)
            .setPositiveButton("OK") { _, _ ->
                // Переход на экран Verification
                findNavController().navigate(R.id.action_forgotFragment_to_verificationFragment)
            }
            .setCancelable(false)
            .show()
    }
}
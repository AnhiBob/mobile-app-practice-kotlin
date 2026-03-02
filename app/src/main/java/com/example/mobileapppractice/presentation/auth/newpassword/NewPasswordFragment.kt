package com.example.mobileapppractice.presentation.auth.newpassword

/**
 * Фрагмент создания нового пароля
 * Обрабатывает ввод и подтверждение нового пароля
 *
 * @author Баданин
 * @since 2026-03-02
 */

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapppractice.R
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        // Проверка совпадения паролей в реальном времени
        setupPasswordValidation()

        // Кнопка сохранить
        binding.saveButton.setOnClickListener {
            if (validatePasswords()) {
                saveNewPassword()
            }
        }
    }

    private fun setupPasswordValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePasswordMatch()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.passwordInput.addTextChangedListener(textWatcher)
        binding.confirmPasswordInput.addTextChangedListener(textWatcher)
    }

    private fun validatePasswordMatch() {
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if (confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                binding.confirmPasswordLayout.error = null
                binding.confirmPasswordLayout.isErrorEnabled = false
            } else {
                binding.confirmPasswordLayout.error = "Пароли не совпадают"
                binding.confirmPasswordLayout.isErrorEnabled = true
            }
        } else {
            binding.confirmPasswordLayout.error = null
            binding.confirmPasswordLayout.isErrorEnabled = false
        }
    }

    private fun validatePasswords(): Boolean {
        val password = binding.passwordInput.text.toString().trim()
        val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

        if (password.isEmpty()) {
            showErrorDialog(getString(R.string.error_password_required))
            return false
        }

        if (confirmPassword.isEmpty()) {
            showErrorDialog("Подтвердите пароль")
            return false
        }

        if (password != confirmPassword) {
            showErrorDialog(getString(R.string.error_password_mismatch))
            return false
        }

        if (password.length < 6) {
            showErrorDialog("Пароль должен содержать минимум 6 символов")
            return false
        }

        return true
    }

    private fun saveNewPassword() {
        showLoading()

        // Имитация запроса к серверу
        binding.root.postDelayed({
            hideLoading()

            // Показываем сообщение об успехе
            Toast.makeText(requireContext(), "Пароль успешно изменен!", Toast.LENGTH_SHORT).show()

            // Переход на экран входа
            findNavController().navigate(R.id.action_newPasswordFragment_to_loginFragment)

        }, 1500)
    }
}
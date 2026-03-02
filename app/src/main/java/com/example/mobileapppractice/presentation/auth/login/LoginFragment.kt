package com.example.mobileapppractice.presentation.auth.login

/**
 * Фрагмент входа пользователя
 * Обрабатывает ввод email и пароля, валидацию и навигацию
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapppractice.R
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        // Валидация email при вводе
        binding.emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Кнопка входа
        binding.loginButton.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        // Переход на восстановление пароля
        binding.forgotLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        // Переход на регистрацию
        binding.registerLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateEmail(email: String) {
        if (email.isNotEmpty() && !emailPattern.matcher(email).matches()) {
            binding.emailLayout.error = getString(R.string.error_invalid_email)
        } else {
            binding.emailLayout.error = null
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (email.isEmpty()) {
            showErrorDialog(getString(R.string.error_email_required))
            return false
        }
        if (!emailPattern.matcher(email).matches()) {
            showErrorDialog(getString(R.string.error_invalid_email))
            return false
        }
        if (password.isEmpty()) {
            showErrorDialog(getString(R.string.error_password_required))
            return false
        }
        return true
    }

    private fun performLogin() {
        showLoading()
        binding.root.postDelayed({
            hideLoading()
            showErrorDialog("Вход выполнен успешно! (тест)")
        }, 1500)
    }
}
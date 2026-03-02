package com.example.mobileapppractice.presentation.auth.login

/**
 * Фрагмент входа пользователя
 * Обрабатывает ввод email и пароля, валидацию и отправку на сервер
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
import com.example.mobileapppractice.R
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    // Регулярное выражение для валидации email (только маленькие буквы и цифры)
    private val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    /**
     * Настройка всех слушателей событий
     */
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

        // Переход на восстановление пароля - пока закомментируем
        binding.forgotLink.setOnClickListener {
            // Пока просто покажем сообщение
            showErrorDialog("Переход на восстановление пароля")
            // it.findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        // Переход на регистрацию - пока закомментируем
        binding.registerLink.setOnClickListener {
            // Пока просто покажем сообщение
            showErrorDialog("Переход на регистрацию")
            // it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    /**
     * Проверка корректности email
     */
    private fun validateEmail(email: String) {
        if (email.isNotEmpty() && !emailPattern.matcher(email).matches()) {
            binding.emailLayout.error = "Некорректный email"
        } else {
            binding.emailLayout.error = null
        }
    }

    /**
     * Проверка всех полей перед входом
     */
    private fun validateInputs(): Boolean {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        return when {
            email.isEmpty() -> {
                showErrorDialog("Введите email")
                false
            }
            !emailPattern.matcher(email).matches() -> {
                showErrorDialog("Некорректный email. Используйте формат: имя@домен.ру")
                false
            }
            password.isEmpty() -> {
                showErrorDialog("Введите пароль")
                false
            }
            else -> true
        }
    }

    /**
     * Имитация входа в систему
     */
    private fun performLogin() {
        showLoading()

        binding.root.postDelayed({
            hideLoading()
            showErrorDialog("Вход выполнен успешно! (тест)")
        }, 1500)
    }
}
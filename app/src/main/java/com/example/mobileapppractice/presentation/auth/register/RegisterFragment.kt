package com.example.mobileapppractice.presentation.auth.register

/**
 * Фрагмент регистрации нового пользователя
 * Обрабатывает ввод данных, валидацию и отправку на сервер
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
import com.example.mobileapppractice.databinding.FragmentRegisterBinding
import java.util.regex.Pattern

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        updateRegisterButtonState()
    }

    private fun setupListeners() {
        binding.emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.consentCheckbox.setOnCheckedChangeListener { _, isChecked ->
            binding.registerButton.isEnabled = isChecked
        }

        binding.registerButton.setOnClickListener {
            if (validateInputs()) {
                performRegistration()
            }
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun validateEmail(email: String) {
        if (email.isNotEmpty() && !emailPattern.matcher(email).matches()) {
            binding.emailLayout.error = getString(R.string.error_invalid_email)
        } else {
            binding.emailLayout.error = null
        }
    }

    private fun updateRegisterButtonState() {
        binding.registerButton.isEnabled = binding.consentCheckbox.isChecked
    }

    private fun validateInputs(): Boolean {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (name.isEmpty()) {
            showErrorDialog(getString(R.string.error_name_required))
            return false
        }
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

    private fun performRegistration() {
        showLoading()

        // Имитация запроса к серверу
        binding.root.postDelayed({
            hideLoading()

            // Пункт 7: Переход на Sign In после успешной регистрации
            Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }, 2000)
    }
}
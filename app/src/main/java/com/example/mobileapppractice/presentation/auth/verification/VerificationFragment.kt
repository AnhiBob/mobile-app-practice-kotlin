package com.example.mobileapppractice.presentation.auth.verification

/**
 * Фрагмент верификации OTP кода
 * Обрабатывает ввод кода подтверждения и таймер
 *
 * @author Баданин
 * @since 2026-03-02
 */

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapppractice.R
import com.example.mobileapppractice.common.BaseFragment
import com.example.mobileapppractice.databinding.FragmentVerificationBinding

class VerificationFragment : BaseFragment<FragmentVerificationBinding>() {

    private lateinit var timer: CountDownTimer
    private val correctCode = "123456" // Для теста правильный код

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVerificationBinding {
        return FragmentVerificationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOtpInputs()
        startTimer()
    }

    private fun setupOtpInputs() {
        val otpFields = listOf(
            binding.otp1, binding.otp2, binding.otp3,
            binding.otp4, binding.otp5, binding.otp6
        )

        for (i in otpFields.indices) {
            otpFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        if (i < otpFields.size - 1) {
                            otpFields[i + 1].requestFocus()
                        } else {
                            // Последнее поле заполнено - проверяем код
                            checkOtpCode()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.resendText.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        binding.timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
        binding.resendText.isEnabled = false
        binding.resendText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.timerText.text = String.format("00:%02d", seconds)
            }

            override fun onFinish() {
                binding.timerText.text = "00:00"
                binding.resendText.isEnabled = true
                binding.resendText.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
            }
        }.start()
    }

    private fun resetTimer() {
        timer.cancel()
        clearOtpFields()
        startTimer()
    }

    private fun clearOtpFields() {
        val otpFields = listOf(
            binding.otp1, binding.otp2, binding.otp3,
            binding.otp4, binding.otp5, binding.otp6
        )
        otpFields.forEach { it.text.clear() }
        binding.otp1.requestFocus()
    }

    private fun checkOtpCode() {
        val enteredCode = binding.otp1.text.toString() +
                binding.otp2.text.toString() +
                binding.otp3.text.toString() +
                binding.otp4.text.toString() +
                binding.otp5.text.toString() +
                binding.otp6.text.toString()

        if (enteredCode.length == 6) {
            if (enteredCode == correctCode) {
                // Правильный код - переход на создание нового пароля
                Toast.makeText(requireContext(), "Код верный!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_verificationFragment_to_newPasswordFragment)
            } else {
                // Неправильный код - все квадраты красные
                makeAllFieldsRed()
                Toast.makeText(requireContext(), "Неверный код!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeAllFieldsRed() {
        val otpFields = listOf(
            binding.otp1, binding.otp2, binding.otp3,
            binding.otp4, binding.otp5, binding.otp6
        )
        otpFields.forEach {
            it.background.setTint(ContextCompat.getColor(requireContext(), R.color.error))
        }

        // Возвращаем обычный цвет через 1 секунду
        binding.root.postDelayed({
            otpFields.forEach {
                it.background.setTint(ContextCompat.getColor(requireContext(), R.color.primary))
            }
        }, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}
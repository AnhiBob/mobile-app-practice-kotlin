package com.example.mobileapppractice.common

/**
 * Базовый класс для всех фрагментов приложения
 * Содержит общую логику для работы с диалогами и индикацией загрузки
 *
 * @author баданин
 * @since 2026-03-02
 */

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    private var progressDialog: ProgressDialog? = null

    /**
     * Предоставляет ViewBinding для фрагмента
     */
    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Показывает диалог с ошибкой
     */
    protected fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show()
    }

    /**
     * Показывает индикатор загрузки
     */
    protected fun showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext()).apply {
                setMessage("Загрузка...")
                setCancelable(false)
            }
        }
        progressDialog?.show()
    }

    /**
     * Скрывает индикатор загрузки
     */
    protected fun hideLoading() {
        progressDialog?.dismiss()
    }
}
package com.harshad.mathsolver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harshad.mathsolver.repository.MathSolverRepository

class MathViewModelFactory(private val mathRepository: MathSolverRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MathViewModel(mathRepository) as T
    }
}
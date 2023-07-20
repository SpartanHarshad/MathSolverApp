package com.harshad.mathsolver.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshad.mathsolver.data.local.ResultEntity
import com.harshad.mathsolver.repository.MathSolverRepository
import kotlinx.coroutines.launch

class MathViewModel(private val mathRepository: MathSolverRepository) : ViewModel() {

    val solutions = MutableLiveData<ArrayList<String>?>()
    val previousSolutions = MutableLiveData<List<ResultEntity>?>()

    fun getSolutions(expressions: List<String>): MutableLiveData<ArrayList<String>?> {
        solutions.value?.clear()
        viewModelScope.launch {
            val response = mathRepository.solveExpressionSaveLocal(expressions)
            solutions.postValue(response)
        }
        return solutions
    }

    fun getHistoryData(): MutableLiveData<List<ResultEntity>?> {
        viewModelScope.launch {
            val result = mathRepository.getHistory()
            previousSolutions.postValue(result)
        }
        return previousSolutions
    }
}
package com.harshad.mathsolver.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshad.mathsolver.data.local.MathDao
import com.harshad.mathsolver.data.local.ResultEntity
import com.harshad.mathsolver.data.remote.MathSolverApi
import com.harshad.mathsolver.data.remote.RequestBody
import com.harshad.mathsolver.data.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MathSolverRepository(val mathDao: MathDao) {

    private val apiService: MathSolverApi =
        RetrofitInstance.getInstance().create(MathSolverApi::class.java)

    suspend fun solveExpressionSaveLocal(expressions: List<String>): ArrayList<String>? {
        return try {
            val requestBody = RequestBody(expressions, 10)
            val response = apiService.getExpressionSolution(requestBody)
            saveDataInLocalDB(expressions, response.result)
            response.result
        } catch (e: Exception) {
            null
        }
    }

    private fun saveDataInLocalDB(
        expressions: List<String>,
        result: java.util.ArrayList<String>
    ) {
        var resultStr = ""
        if (result.size == expressions.size) {
            for (i in 0 until result.size) {
                if (result[i] != "undefined") {
                    resultStr += "${expressions[i]} = ${result[i]}" + "\n"
                }
            }
            val resultEntity = ResultEntity(resultStr)
            CoroutineScope(Dispatchers.IO).launch {
                mathDao.saveResult(resultEntity)
            }
        }
    }


    suspend fun getHistory(): List<ResultEntity> {
        return mathDao.getAllResults()
    }

    suspend fun deleteResult(resultEntity: ResultEntity) {
        mathDao.deleteResultRecord(resultEntity)
    }

}
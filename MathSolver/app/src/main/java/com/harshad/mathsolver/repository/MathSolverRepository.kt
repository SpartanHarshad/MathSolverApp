package com.harshad.mathsolver.repository


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


    /**
     * this method fetch solutions from api and store that result in room db
     * */
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

    /**
     * in this method we are storing results in room data with converted string
     * */
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

    /**
     * fetch all local results
     * */
    suspend fun getHistory(): List<ResultEntity> {
        return mathDao.getAllResults()
    }

    suspend fun deleteResult(resultEntity: ResultEntity) {
        mathDao.deleteResultRecord(resultEntity)
    }

}
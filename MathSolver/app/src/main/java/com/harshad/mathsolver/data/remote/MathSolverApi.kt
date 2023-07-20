package com.harshad.mathsolver.data.remote


import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MathSolverApi {
    ///v4/?expr=

    @POST("/v4/?expr=")
    @Headers("content-type: application/json")
    suspend fun getExpressionSolution(@Body requestBody: RequestBody): SolutionResponse

}
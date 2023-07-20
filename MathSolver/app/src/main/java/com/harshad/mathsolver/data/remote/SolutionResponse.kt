package com.harshad.mathsolver.data.remote

import com.google.gson.annotations.SerializedName


data class SolutionResponse (

    @SerializedName("result" ) var result : ArrayList<String> = arrayListOf(),
    @SerializedName("error"  ) var error  : String?           = null

)

package com.harshad.mathsolver

import android.app.Application
import com.harshad.mathsolver.data.local.MathDataBase
import com.harshad.mathsolver.repository.MathSolverRepository

class MathApplication : Application() {

    private val mathDao by lazy {
        val roomDb = MathDataBase.getMathDatabaseInstance(this)
        roomDb.getMathDao()
    }

    val mathSolverRepository by lazy {
        MathSolverRepository(mathDao)
    }
}
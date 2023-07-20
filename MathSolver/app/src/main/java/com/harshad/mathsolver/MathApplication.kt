package com.harshad.mathsolver

import android.app.Application
import com.harshad.mathsolver.repository.MathSolverRepository

class MathApplication : Application() {
     val mathSolverRepository by lazy {
        MathSolverRepository()
    }
}
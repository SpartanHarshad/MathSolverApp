package com.harshad.mathsolver.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.harshad.mathsolver.MathApplication
import com.harshad.mathsolver.databinding.FragmentCalculationBinding
import com.harshad.mathsolver.viewmodel.MathViewModel
import com.harshad.mathsolver.viewmodel.MathViewModelFactory


class CalculationFragment : Fragment() {

    private lateinit var binding: FragmentCalculationBinding
    private lateinit var mathViewModel: MathViewModel
    private lateinit var progressAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalculationBinding.inflate(inflater, container, false)
        initViewModel()
        initView()
        return binding.root
    }

    private fun initViewModel() {
        val mathApp = activity?.application as MathApplication
        val mathRepo = mathApp.mathSolverRepository
        val mathFactory = MathViewModelFactory(mathRepo)
        mathViewModel = ViewModelProvider(requireActivity(), mathFactory)[MathViewModel::class.java]
    }

    private fun initView() {
        binding.btnSolve.setOnClickListener {
            showProgressBar()
            val exp = binding.etvMathExp.text.toString()
            if (exp.isNotEmpty()) {
                val expression = exp.split('\n')
                binding.etvMathExp.setText("")
                mathViewModel.getSolutions(expression).observe(viewLifecycleOwner) { solutions ->
                    if (solutions != null) {
                        progressAlert.hide()
                        var result = ""
                        if (expression.size == solutions.size) {
                            for (i in 0 until solutions.size) {
                                if (solutions[i] != "undefined") {
                                    result += "${expression[i]} = ${solutions[i]}" + "\n"
                                }
                            }
                        }
                        binding.tvSolution.text = result
                    } else {
                        progressAlert.hide()
                        Toast.makeText(
                            context,
                            "something went wrong please check network",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(context, "please add some math expression", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showProgressBar() {
        progressAlert = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
        progressAlert.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalculationFragment()
    }
}
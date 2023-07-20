package com.harshad.mathsolver.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshad.mathsolver.MathApplication
import com.harshad.mathsolver.R
import com.harshad.mathsolver.adapter.HistoryAdapter
import com.harshad.mathsolver.data.local.ResultEntity
import com.harshad.mathsolver.databinding.FragmentHistoryBinding
import com.harshad.mathsolver.viewmodel.MathViewModel
import com.harshad.mathsolver.viewmodel.MathViewModelFactory


class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHistoryBinding
    private var results = ArrayList<ResultEntity>()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var mathViewModel: MathViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        initViewModel()
        initRV()
        observeHistoryData()
        return binding.root
    }

    private fun observeHistoryData() {
        mathViewModel.getHistoryData().observe(viewLifecycleOwner) {
            results.clear()
            if (it != null) {
                results.addAll(it)
                historyAdapter.updateHistoryList(results)
            }
        }
    }

    private fun initViewModel() {
        val mathApp = activity?.application as MathApplication
        val mathRepo = mathApp.mathSolverRepository
        val mathFactory = MathViewModelFactory(mathRepo)
        mathViewModel = ViewModelProvider(requireActivity(), mathFactory)[MathViewModel::class.java]
    }

    private fun initRV() {
        historyAdapter = HistoryAdapter(results, this)
        binding.rvPreviousItems.layoutManager = LinearLayoutManager(context)
        binding.rvPreviousItems.adapter = historyAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onItemClick(resultEntity: ResultEntity) {
        mathViewModel.deleteResultItem(resultEntity)
    }
}
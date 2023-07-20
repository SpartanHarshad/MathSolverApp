package com.harshad.mathsolver.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harshad.mathsolver.data.local.ResultEntity
import com.harshad.mathsolver.databinding.LayoutResultItemBinding

class HistoryAdapter(
    private var results: ArrayList<ResultEntity>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<HistoryAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutResultItemBinding.inflate(inflater, parent, false)
        return ResultViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.setData(results[position], position)
    }

    override fun getItemCount(): Int {
        return results.size;
    }

    fun updateHistoryList(newResults: ArrayList<ResultEntity>) {
        results = newResults
        notifyDataSetChanged()
    }

    inner class ResultViewHolder(private val binding: LayoutResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(resultEntity: ResultEntity, pos: Int) {
            binding.tvResultEntity.text = resultEntity.resultStr
            binding.imgDelete.setOnClickListener {
                results.remove(resultEntity)
                notifyItemChanged(pos)
                onItemClickListener.onItemClick(resultEntity)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(resultEntity: ResultEntity)
    }
}
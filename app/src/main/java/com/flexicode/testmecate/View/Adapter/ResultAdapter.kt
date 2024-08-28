package com.flexicode.testmecate.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexicode.testmecate.Models.Country
import com.flexicode.testmecate.R
import com.flexicode.testmecate.databinding.ItemResultBinding

class ResultAdapter(private val countryList: List<Country>) : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ResultHolder(layoutInflater.inflate(R.layout.item_result, parent, false))
    }

    override fun getItemCount(): Int = countryList.size


    override fun onBindViewHolder(holder: ResultHolder, position: Int) {

        val item = countryList[position]
        holder.bind(item)

    }

    class ResultHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemResultBinding.bind(view)

        fun bind(item: Country){

           binding.txtvIdContry.text = item.countryId
           binding.txtvProbability.text = "% ${String.format("%.4f",item.probability)}"

        }
    }

}
package com.flexicode.testmecate.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexicode.testmecate.Entity.UserEntity
import com.flexicode.testmecate.Models.Country
import com.flexicode.testmecate.R
import com.flexicode.testmecate.View.ItemHistoryFragment
import com.flexicode.testmecate.databinding.ItemHistoryBinding
import com.flexicode.testmecate.databinding.ItemResultBinding

class HistoryAdapter(private val historyList: List<UserEntity>, private val onItemClick: (UserEntity) -> Unit) : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoryHolder(layoutInflater.inflate(R.layout.item_history, parent, false))
    }

    override fun getItemCount(): Int = historyList.size


    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {

        val item = historyList[position]
        holder.bind(item, onItemClick)

    }

    class HistoryHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemHistoryBinding.bind(view)

        fun bind(item: UserEntity, onItemClick: (UserEntity) -> Unit){

            binding.textUser.text = item.name

            itemView.setOnClickListener {
                onItemClick(item)
            }


        }
    }

}
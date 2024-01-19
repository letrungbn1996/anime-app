package com.appkitten.animexa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appkitten.animexa.databinding.LanguagesItemBinding
import com.appkitten.animexa.model.LanguageModel
import com.appkitten.animexa.util.OnItemClickListener

class LanguagesAdapter(private val dataSet: List<LanguageModel>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {

    // Selected item position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LanguagesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClickListener, position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        private val binding: LanguagesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LanguageModel, itemClickListener: OnItemClickListener,position:Int) {
            binding.langTitle.text = item.languageName
            binding.langIcon.setImageResource(item.drawableResId)
            binding.checked.isChecked = item.isSelected

            binding.root.setOnClickListener {
                if (!item.isSelected){
                    itemClickListener.onItemClick(position)
                }
            }
        }
    }
}

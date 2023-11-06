package com.example.artisthub.features.searchartist.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.databinding.ArtistViewBinding
import com.example.artisthub.features.searchartist.data.model.response.Artist

class ArtistViewHolder(private val binding: ArtistViewBinding, private val itemClick: ArtistClickListener) : RecyclerView.ViewHolder(binding.root) {

    interface ArtistClickListener {
        fun onArtistClick(item: Artist, position: Int)
    }

    fun bind(item: Artist, position: Int) {
        binding.apply {
            tvArtistName.text = item.name ?: "N/A"
            tvTypeDet.text = item.type ?: "N/A"
            tvCountryDet.text = item.country ?: "N/A"
            tvScoreDet.text = "${item.score ?: "N/A"}"
            itemView.setOnClickListener {
                itemClick.onArtistClick(item, position)
            }
        }
    }

}

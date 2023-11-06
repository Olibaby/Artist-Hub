package com.example.artisthub.features.searchartist.presentation.viewholder

import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.databinding.ArtistViewBinding
import com.example.artisthub.databinding.SearchViewBinding
import com.example.artisthub.features.searchartist.data.model.response.Artist
import com.example.artisthub.features.searchartist.presentation.adapter.Fields

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = SearchViewBinding.bind(itemView)
    val search = binding.searchView
}




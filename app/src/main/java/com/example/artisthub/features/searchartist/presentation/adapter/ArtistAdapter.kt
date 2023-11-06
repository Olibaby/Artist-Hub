package com.example.artisthub.features.searchartist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.R
import com.example.artisthub.databinding.ArtistViewBinding
import com.example.artisthub.features.searchartist.data.model.response.Artist
import com.example.artisthub.features.searchartist.presentation.viewholder.ArtistViewHolder

class ArtistAdapter(private val artistList: MutableList<Artist>, private val clickListener: ArtistViewHolder.ArtistClickListener): RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(ArtistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = artistList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return artistList.count()
    }

    fun updateArtist(newData: List<Artist>){
        artistList.clear()
        artistList.addAll(newData)
        notifyDataSetChanged()
    }
}
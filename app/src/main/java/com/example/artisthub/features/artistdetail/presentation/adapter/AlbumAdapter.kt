package com.example.artisthub.features.artistdetail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.R
import com.example.artisthub.features.artistdetail.data.model.album.response.Release
import com.example.artisthub.features.artistdetail.presentation.viewholder.AlbumViewHolder

class AlbumAdapter(private val albumList: MutableList<Release>): RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.album_view,parent,false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = albumList[position]
        holder.title.text = item.title ?: "N/A"
        holder.status.text = item.status ?: "N/A"
        holder.tracks.text = "${item.trackCount ?: "N/A"}"
        holder.date.text = item.date ?: "N/A"
        holder.country.text = item.country ?: "N/A"
        holder.score.text = "${item.score?: "N/A"}"
        holder.disambiguation.text = item.disambiguation ?: "N/A"
        holder.barcode.text = item.barcode ?: "N/A"
    }

    override fun getItemCount(): Int {
        return albumList.count()
    }

    fun updateAlbum(newData: List<Release>){
        albumList.clear()
        albumList.addAll(newData)
        notifyDataSetChanged()
    }
}


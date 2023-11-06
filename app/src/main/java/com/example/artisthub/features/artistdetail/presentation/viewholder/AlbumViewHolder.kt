package com.example.artisthub.features.artistdetail.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.databinding.AlbumViewBinding

class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = AlbumViewBinding.bind(itemView)
    var title = binding.tvAlbumTitleValue
    var status = binding.tvAlbumStatusValue
    var tracks = binding.tvAlbumTracksValue
    var date = binding.tvAlbumDateValue
    var country = binding.tvAlbumCountryValue
    var disambiguation = binding.tvAlbumDisValue
    var barcode = binding.tvAlbumBarcodeValue
    var score = binding.tvAlbumScoreValue
}
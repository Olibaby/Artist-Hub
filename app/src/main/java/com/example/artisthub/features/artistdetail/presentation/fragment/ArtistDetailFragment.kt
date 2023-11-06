package com.example.artisthub.features.artistdetail.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.R
import com.example.artisthub.databinding.FragmentArtistDetailBinding
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.presentation.adapter.AlbumAdapter
import com.example.artisthub.features.artistdetail.presentation.viewmodel.ArtistAlbumViewModel
import com.example.artisthub.features.searchartist.data.model.response.Artist
import com.example.artisthub.core.base.BaseFragment
import com.example.artisthub.core.utils.view.observeChange
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArtistDetailFragment : BaseFragment() {
    private val artistAlbumViewModel: ArtistAlbumViewModel by viewModel()
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var artist: Artist
    private val albumAdapter by lazy { AlbumAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArtistFromArgs()
        showDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        setupToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeViewModel()
        updateView()
    }

    private fun setupToolbar() {
        val navController = findNavController()
        val appBarConf = AppBarConfiguration(navController.graph)
        _binding?.toolbar?.let {
            it.setupWithNavController(navController, appBarConf)
            it.setNavigationIcon(R.drawable.arrowleft)
        }
        _binding?.toolbarTitle?.text = artist.name
    }

    private fun getArtistFromArgs() {
        val artistInit: Artist? = arguments?.getSerializable(ARG_SELECTED_ARTIST) as? Artist
        artistInit?.let {
            artist = it
            artistAlbumViewModel.getAlbums(GetAlbumsQuery(it.id ?: ""))
        }
    }

    private fun observeViewModel() {
        artistAlbumViewModel.showLoading.observeChange(viewLifecycleOwner){
            if (it){ alertDialog.show() } else { alertDialog.dismiss() }
        }

        artistAlbumViewModel.showError.observeChange(viewLifecycleOwner){
            showToast(it)
        }

        artistAlbumViewModel.getAlbumsResponse.observeChange(viewLifecycleOwner){
            it.releases?.let { it1 -> albumAdapter.updateAlbum(it1) }
            _binding?.tvDetailsPageBeginValue?.text = "${it.releaseCount ?: "N/A"}"
        }
    }

    private fun setRecyclerView() {
        _binding?.albumRecycler?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        _binding?.albumRecycler?.adapter = albumAdapter
    }

    private fun updateView() {
        _binding?.apply {
            tvDetailsPageTypeValue.text = artist.type ?: "N/A"
            tvDetailsPageScoreValue.text = "${artist.score ?: "N/A"}"
            tvDetailsPageSNValue.text = artist.sortName ?: "N/A"
            tvDetailsPageCountryValue.text = artist.country ?: "N/A"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SELECTED_ARTIST = "selected_artist"
    }
}
package com.example.artisthub.features.searchartist.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.R
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.databinding.FragmentSearchArtistBinding
import com.example.artisthub.features.searchartist.data.model.response.Artist
import com.example.artisthub.features.searchartist.presentation.adapter.ArtistAdapter
import com.example.artisthub.features.searchartist.presentation.adapter.Fields
import com.example.artisthub.features.searchartist.presentation.adapter.SearchAdapter
import com.example.artisthub.core.base.BaseFragment
import com.example.artisthub.core.utils.view.observeChange
import com.example.artisthub.core.utils.view.swipeToDelete
import com.example.artisthub.features.searchartist.presentation.viewholder.ArtistViewHolder
import com.example.artisthub.features.searchartist.presentation.viewmodel.SearchArtistViewModel
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchArtistFragment : BaseFragment(), ArtistViewHolder.ArtistClickListener {
    private val searchArtistViewModel: SearchArtistViewModel by viewModel()
    private var _binding: FragmentSearchArtistBinding? = null
    private val binding get() = _binding!!
    private var emptyMutableMap = mutableMapOf<String, String>()
    private var query = ""
    private val searchAdapter by lazy { SearchAdapter(mutableListOf(Fields("artist"))) {
        emptyMutableMap = it
      }
    }
    private val artistAdapter by lazy { ArtistAdapter(mutableListOf(), this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDialog()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchArtistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeViewModel()
        updateView()
    }

    private fun updateView() {
        _binding?.button?.setOnClickListener {
            showMenu(it, R.menu.popup_menu)
        }
        _binding?.searchBtn?.setOnClickListener {
            getQuery()
            searchArtist()
        }
    }

    private fun searchArtist(){
        artistAdapter.updateArtist(listOf())
        if (query.isNotEmpty()){
            binding.tvEmptyArtistSearch.visibility = View.GONE
            searchArtistViewModel.searchArtist(SearchArtistQuery(query))
            query = ""
        } else{
            showToast("Enter Query")
        }
    }

    private fun observeViewModel() {
        searchArtistViewModel.showLoading.observeChange(viewLifecycleOwner){
            if (it){ alertDialog.show() } else { alertDialog.dismiss() }
        }

        searchArtistViewModel.showError.observeChange(viewLifecycleOwner){
             showToast(it)
        }

        searchArtistViewModel.searchArtistResponse.observeChange(viewLifecycleOwner){
            it.artists?.let { it1 ->
                showEmpty(it.artists)
                artistAdapter.updateArtist(it1)
            }
        }
    }

    private fun setRecyclerView() {
        _binding?.searchRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        _binding?.searchRecyclerView?.adapter = searchAdapter
        _binding?.searchRecyclerView?.let { this.swipeToDelete(it, searchAdapter, searchAdapter.fieldsListField, searchAdapter::removeAt) }

        _binding?.artistRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        _binding?.artistRecyclerView?.adapter = artistAdapter

    }

    private fun showEmpty(items: List<Artist>) {
        binding.let {
            val hasItems = items.isNotEmpty()
            val recyclerVisib = if (hasItems) View.VISIBLE else View.GONE
            val emptyViewVisib = if (hasItems) View.GONE else View.VISIBLE
            it.artistRecyclerView.visibility = recyclerVisib
            it.tvEmptyArtistSearch.visibility = emptyViewVisib
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            when (menuItem!!.itemId) {
                R.id.option_1 -> {
                    searchAdapter.updateSearch(Fields(menuItem.title.toString()))
                }
                R.id.option_2 -> {
                    searchAdapter.updateSearch(Fields(menuItem.title.toString()))
                }
                R.id.option_3 -> {
                    searchAdapter.updateSearch(Fields(menuItem.title.toString()))
                }
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun getQuery(){
        emptyMutableMap.onEachIndexed { index, entry ->
            query = query + entry.key+ ":" + entry.value
            if(index < emptyMutableMap.size - 1) {
                query += " AND "
            }
        }
    }

    override fun onArtistClick(item: Artist, position: Int) {
        val action = SearchArtistFragmentDirections.actionMySearchArtistFragmentToMyArtistDetailFragment(item)
        emptyMutableMap = mutableMapOf()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }
}
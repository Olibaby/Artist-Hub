package com.example.artisthub.features.searchartist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.artisthub.R
import com.example.artisthub.features.searchartist.presentation.viewholder.SearchViewHolder

class SearchAdapter(private val fieldsList: MutableList<Fields>, private val emptyMutableMap: (MutableMap<String, String>) -> Unit): RecyclerView.Adapter<SearchViewHolder>() {
    private val fieldMutableMap = mutableMapOf<String, String>()
    var fieldsListField = fieldsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.search_view,parent,false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = fieldsList[position]
        holder.search.queryHint ="Search by ${item.name}"
        holder.search.setQuery("", false)

        holder.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                fieldMutableMap[item.name] = newText ?: ""
                emptyMutableMap(fieldMutableMap)
                println(fieldMutableMap)
                return true
            }

        })
    }

    override fun getItemCount(): Int {
        return fieldsList.count()
    }

    fun updateSearch(newData: Fields){
        if (!fieldsList.contains(newData)){
            fieldsList.add(newData)
            notifyDataSetChanged()
        }
    }

    fun removeAt(position: Int) {
        val fieldAtPosition = fieldsList[position]
        fieldMutableMap.forEach {
            if (it.key == fieldAtPosition.name) {
                fieldMutableMap.remove(it.key)
            }
        }
        emptyMutableMap(fieldMutableMap)
        notifyItemRemoved(position)
        fieldsList.removeAt(position)
        fieldsListField = fieldsList
    }

}

data class Fields(val name: String)
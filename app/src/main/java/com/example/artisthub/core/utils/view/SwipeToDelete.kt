package com.example.artisthub.core.utils.view


import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun <T:Any, R:RecyclerView.ViewHolder> Fragment.swipeToDelete(recyclerView: RecyclerView,adapter: RecyclerView.Adapter<R>, fieldList: MutableList<T>, removeAt: (Int) -> Unit){
    //create item touch helper, specifying drag direction and position to right
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // call when the item is moved.
            return false
        }

        // method to add swipe to delete functionality
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //get the item at a particular position.
            val deletedField: T =
                fieldList.get(viewHolder.adapterPosition)

            //get the position of the item at that position.
            val position = viewHolder.adapterPosition

            // notify our item is removed from adapter.
            removeAt(position)

            // below line is to display our snackbar with action.
            Snackbar.make(recyclerView, "Deleted ", Snackbar.LENGTH_LONG)
                .setAction(
                    "Undo",
                    View.OnClickListener {
                        //add our item to array list with a position.
                        fieldList.add(position, deletedField)

                        //notify item is added to our adapter class.
                        adapter.notifyItemInserted(position)
                    }).show()

        }
    }).attachToRecyclerView(recyclerView)
}




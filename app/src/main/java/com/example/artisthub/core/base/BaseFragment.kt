package com.example.artisthub.core.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog

open class BaseFragment: Fragment() {
    lateinit  var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun showDialog(){
        alertDialog = SpotsDialog.Builder()
            .setContext(context)
            .setMessage("Loading...")
            .setCancelable(false)
            .build()
    }

    fun showToast(msg: String){
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

}
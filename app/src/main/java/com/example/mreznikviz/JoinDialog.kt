package com.example.mreznikviz

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.widget.TextView



class JoinDialog(context: Context) : Dialog(context), View.OnClickListener {
    var join: TextView? = null
    var cancel: TextView? = null


    override fun onClick(v: View) {
        if(v.id == R.id.joinButtonDialog){
            (scanForActivity(context) as MainActivity).doJoin()
        }
        dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_dialog_layout)
        join = findViewById(R.id.joinButtonDialog)
        cancel = findViewById(R.id.cancelButtonDialog)
        join?.setOnClickListener(this)
        cancel?.setOnClickListener(this)
    }

    private fun scanForActivity(cont: Context?): Activity? {
        if (cont == null)
            return null
        else if (cont is Activity)
            return cont
        else if (cont is ContextWrapper)
            return scanForActivity(cont.baseContext)

        return null
    }

}

package com.example.mreznikviz

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button

class JoinDialog(context: Context) : Dialog(context), DialogInterface.OnClickListener {
    var join: Button? = null
    var cancel: Button? = null

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if(which == R.id.joinButtonDialog){
            (context as MainActivity).doJoin()
        }
        dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_dialog_layout)
        join = findViewById(R.id.joinButtonDialog)
        cancel = findViewById(R.id.cancelButtonDialog)
    }


}
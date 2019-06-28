package com.example.mreznikviz

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button

class JoinDialog(context: Context) : Dialog(context), View.OnClickListener {
    var join: Button? = null
    var cancel: Button? = null

    override fun onClick(v: View) {
        if(v.id == R.id.joinButtonDialog){
            (ownerActivity as MainActivity).doJoin()
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

}

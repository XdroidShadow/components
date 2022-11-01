package com.xdroid.spring.util.android.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.xdroid.spring.R

/**
 *   通用对话框
 */
class XDDialog : Dialog {
    private var dialog: View? = null
    private var title: String?
    private var subTitle: String?
    private var positiveStr: String? = null
    private var negativeStr: String? = null
    private var isCanHide = true

    //所有控件
    private var alert_title: TextView? = null
    private var alert_message: TextView? = null
    private var alert_confirm: TextView? = null
    private var alert_cancel: TextView? = null
    private var callBack: Action?

    @JvmOverloads
    constructor(
        context: Context,
        title: String?,
        subTile: String?,
        positiveStr: String? = null,
        negativeStr: String? = null,
        isCanHide: Boolean = false,
        listener: Action?
    ) : super(context) {
        this.title = title
        subTitle = subTile
        this.positiveStr = positiveStr
        this.negativeStr = negativeStr
        callBack = listener
        this.isCanHide = isCanHide
    }


    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog = layoutInflater.inflate(R.layout.dialog_alert_layout, null)
        setContentView(dialog!!)
        initDialog()
    }

    private fun initDialog() {
        alert_title = findViewById(R.id.alert_title)
        alert_message = findViewById(R.id.alert_message)
        alert_confirm = findViewById(R.id.alert_confirm)
        alert_cancel = findViewById(R.id.alert_cancel)
        if (title != null && title != "") {
            alert_title?.setText(title)
        }
        if (subTitle != null && subTitle != "") {
            alert_message?.setText(subTitle)
        }
        if (positiveStr != null && positiveStr != "") {
            alert_confirm?.setText(positiveStr)
        }
        if (negativeStr != null && negativeStr != "") {
            alert_cancel?.setText(negativeStr)
        }
        alert_confirm?.setOnClickListener {
            if (callBack != null) {
                dismiss()
                callBack!!.onPositiveClick()
            }
        }
        alert_cancel?.setOnClickListener {
            if (callBack != null) {
                dismiss()
                callBack!!.onNegativeClick()
            }
        }
    }

    override fun onBackPressed() {
        if (isCanHide) {
            dismiss()
        }
    }

    interface Action {
        fun onPositiveClick()
        fun onNegativeClick()
    }

    open class CallBack:Action{
        override fun onNegativeClick() {

        }

        override fun onPositiveClick() {

        }
    }

    override fun show() {
        super.show()
    }

}
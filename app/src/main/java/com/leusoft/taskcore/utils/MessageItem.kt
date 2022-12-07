package com.leusoft.taskcore.utils

import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.leusoft.taskcore.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat


abstract class MessageItem(private val message: Message)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }

    private fun setTimeText(viewHolder: ViewHolder) {
        val dateFormat = SimpleDateFormat
            .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        val tvMessageTime = viewHolder.itemView.find<TextView>(R.id.textView_message_time)
        tvMessageTime.text = dateFormat.format(message.time)
    }

    private fun setMessageRootGravity(viewHolder: ViewHolder) {
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
            viewHolder.itemView.findViewById<RelativeLayout>(R.id.message_root).apply {
                backgroundResource = R.drawable.round_outline_shape

                val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)
                this.layoutParams = lParams
            }
        }
        else {
            viewHolder.itemView.findViewById<RelativeLayout>(R.id.message_root).apply {
                backgroundResource = R.drawable.round_outline_shape


                val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
                this.layoutParams = lParams
            }
        }
    }
}
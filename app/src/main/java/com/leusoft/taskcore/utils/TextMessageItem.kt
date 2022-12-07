package com.leusoft.taskcore.utils

import android.content.Context
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.leusoft.taskcore.R
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import org.jetbrains.anko.backgroundResource
import java.text.SimpleDateFormat


class TextMessageItem(val message: TextMessage,
                      val context: Context
)
    : MessageItem(message) {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.textView_message_text).text = message.text

        super.bind(viewHolder, position)
    }



    override fun getLayout() = R.layout.item_text_message


    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is TextMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? TextMessageItem)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }

}
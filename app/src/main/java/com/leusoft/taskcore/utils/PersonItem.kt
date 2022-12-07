package com.leusoft.taskcore.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.leusoft.taskcore.R
import com.leusoft.taskcore.model.FireUser
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


class PersonItem (val person: FireUser,
                  val userId: String,
                  private val context: Context) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.userListTextView).text = person.name
        if(person.profilePicturePath!=null)
        {
            var tmpImageView = viewHolder.itemView.findViewById<ImageView>(R.id.userListImageView)
            GlideApp.with(context)
                .load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.ic_hard_avatar)
                .into(tmpImageView)

        }

    }

    override fun getLayout() = R.layout.item_person

}
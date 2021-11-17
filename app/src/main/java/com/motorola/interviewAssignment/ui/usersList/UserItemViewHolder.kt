package com.motorola.interviewAssignment.ui.usersList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.motorola.interviewAssignment.R
import com.motorola.interviewAssignment.domain.models.UserItem

private const val TITLE_DEFAULT = "LOADING"
private const val SUB_TITLE_DEFAULT = "LOADING"

class UserItemViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {

    val name: TextView = itemView.findViewById(R.id.user_name)
    val email: TextView = itemView.findViewById(R.id.user_email)
    val userImage: ImageView = itemView.findViewById(R.id.user_image)

    fun bind(item: UserItem?) {
        if (item == null) {
            name.text = TITLE_DEFAULT
            email.text = SUB_TITLE_DEFAULT
        } else {
            name.text = item.name
            email.text = item.email

            Glide.with(userImage.context)
                .load(item.userPictureUrl)
                .thumbnail(Glide.with(userImage.context).load(item.userThumbnailPictureUrl))
                .error(R.drawable.image_error)
                .placeholder(R.drawable.image_loading)
                .into(userImage)

        }
    }

}
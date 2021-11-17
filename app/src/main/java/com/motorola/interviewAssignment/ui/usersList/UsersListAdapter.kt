package com.motorola.interviewAssignment.ui.usersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.motorola.interviewAssignment.R
import com.motorola.interviewAssignment.domain.models.UserItem

class UsersListAdapter(
    diffCallback: DiffUtil.ItemCallback<UserItem>,
) : PagingDataAdapter<UserItem, UserItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item)
    }

}
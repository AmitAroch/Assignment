package com.motorola.interviewAssignment.domain.models

import androidx.recyclerview.widget.DiffUtil

object UserItemComperator : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldDetails: UserItem, newDetails: UserItem): Boolean {
        return oldDetails == newDetails
    }
}
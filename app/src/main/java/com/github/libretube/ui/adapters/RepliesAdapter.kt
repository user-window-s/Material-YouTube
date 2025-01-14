package com.github.libretube.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.libretube.databinding.RepliesRowBinding
import com.github.libretube.extensions.formatShort
import com.github.libretube.ui.viewholders.RepliesViewHolder
import com.github.libretube.util.ImageHelper
import com.github.libretube.util.NavigationHelper

class RepliesAdapter(
    private val replies: MutableList<com.github.libretube.api.obj.Comment>
) : RecyclerView.Adapter<RepliesViewHolder>() {

    fun clear() {
        val size: Int = replies.size
        replies.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun updateItems(newItems: List<com.github.libretube.api.obj.Comment>) {
        var repliesSize = replies.size
        replies.addAll(newItems)
        notifyItemRangeInserted(repliesSize, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RepliesRowBinding.inflate(layoutInflater, parent, false)
        return RepliesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RepliesViewHolder, position: Int) {
        holder.binding.apply {
            val reply = replies[position]
            commentInfos.text =
                reply.author.toString() +
                " • " + reply.commentedTime.toString()
            commentText.text =
                reply.commentText.toString()
            ImageHelper.loadImage(reply.thumbnail, commentorImage)
            likesTextView.text =
                reply.likeCount?.toLong().formatShort()
            if (reply.verified == true) {
                verifiedImageView.visibility = View.VISIBLE
            }
            if (reply.pinned == true) {
                pinnedImageView.visibility = View.VISIBLE
            }
            if (reply.hearted == true) {
                heartedImageView.visibility = View.VISIBLE
            }
            commentorImage.setOnClickListener {
                NavigationHelper.navigateVideo(root.context, reply.commentorUrl)
            }
        }
    }

    override fun getItemCount(): Int {
        return replies.size
    }
}

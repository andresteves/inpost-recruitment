package pl.inpost.recruitmenttask.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

inline fun <reified T: Any>  simpleDiffUtil() = object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem::class == newItem::class

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

}
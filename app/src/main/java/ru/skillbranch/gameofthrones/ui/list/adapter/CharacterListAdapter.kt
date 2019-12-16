package ru.skillbranch.gameofthrones.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.ui.list.getDrawableHouseIcon

class CharacterListAdapter(val listener: (CharacterItem) -> Unit) :
    ListAdapter<CharacterItem, CharacterListAdapter.CharacterItemViewHolder>(
        CharacterItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return CharacterItemViewHolder(
            inflater.inflate(
                R.layout.list_item_character,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        val characterItem = getItem(position)
        holder.bind(characterItem, listener)
    }


    inner class CharacterItemViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(item: CharacterItem, listener: (CharacterItem) -> Unit) {
//            Glide.with(itemView)
//                .load(getDrawableHouseIcon(item.house))
//                .into()
        }

    }

}

private class CharacterItemDiffCallback : DiffUtil.ItemCallback<CharacterItem>() {
    override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
        return newItem == oldItem
    }

}


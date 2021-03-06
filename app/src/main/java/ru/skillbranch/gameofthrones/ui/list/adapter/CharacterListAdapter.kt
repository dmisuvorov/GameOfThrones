package ru.skillbranch.gameofthrones.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_character.view.*
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.util.getDrawableHouseIcon

class CharacterListAdapter(private val listener: (CharacterItem) -> Unit) :
    ListAdapter<CharacterItem, CharacterListAdapter.CharacterItemViewHolder>(
        CharacterItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
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
        private var titles: String = ""
        private var aliases: String = ""

        fun bind(item: CharacterItem, listener: (CharacterItem) -> Unit) {
            Glide.with(itemView)
                .load(getDrawableHouseIcon(item.house))
                .into(itemView.iv_house)
            itemView.tv_character_name.text = item.name.ifEmpty { "Information is unknown" }

            titles = item.titles.filter { it.isNotEmpty() }.joinToString(" • ")
            aliases = item.aliases.filter { it.isNotEmpty() }.joinToString(" • ")
            if (titles.isNotEmpty() && aliases.isNotEmpty()) titles = titles.plus(" • ")
            itemView.tv_character_titles.text =
                titles.plus(aliases).ifEmpty { "Information is unknown" }

            itemView.setOnClickListener { listener.invoke(item) }
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


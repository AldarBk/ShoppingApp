package com.example.shoppingapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemShopDisabledBinding
import com.example.shoppingapp.databinding.ItemShopEnabledBinding
import com.example.shoppingapp.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_IS_DISABLED -> R.layout.item_shop_disabled
            VIEW_IS_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        when (binding) {
            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }

            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_IS_ENABLED
        } else {
            VIEW_IS_DISABLED
        }
    }


    companion object {
        const val VIEW_IS_ENABLED = 100
        const val VIEW_IS_DISABLED = 101


        const val MAX_POOL_SIZE = 15
    }

}
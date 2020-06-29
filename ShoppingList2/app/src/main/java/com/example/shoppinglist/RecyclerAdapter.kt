package com.example.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import kotlinx.android.synthetic.main.shopping_item.view.*

class RecyclerAdapter: RecyclerView.Adapter<ShoppingListViewHolder>() {

    var shoppingList = listOf<RVShoppingItem>()
    var onLongClick: ((RVShoppingItem) -> Unit)? = null
    var onCheckChange: ((RVShoppingItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        var shoppingItem = shoppingList[position]

        holder.itemView.cbItemGot.isChecked = shoppingItem.itemGot
        holder.itemView.tvItemName.text = shoppingItem.itemName

        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(shoppingItem)
            return@setOnLongClickListener true
        }

        holder.itemView.cbItemGot.setOnCheckedChangeListener { _, isChecked ->
            shoppingItem.itemGot = isChecked
            onCheckChange?.invoke(shoppingItem)
        }
    }

}

class ShoppingListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
}
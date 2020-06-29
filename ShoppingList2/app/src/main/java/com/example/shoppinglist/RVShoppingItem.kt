package com.example.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rv_shopping_list_table")
class RVShoppingItem (
    //auto-generated ID for the database
    @PrimaryKey(autoGenerate = true) val itemID: Int? = null,
    //boolean value for checkbox
    @ColumnInfo(name = "item_got") var itemGot: Boolean = false,
    //string value for title
    @ColumnInfo(name = "item_name") var itemName: String = ""
)
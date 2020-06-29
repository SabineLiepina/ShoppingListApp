package com.example.shoppinglist

import androidx.room.*

@Dao
interface ShoppingDAO {
    @Query("SELECT * from rv_shopping_list_table")
    //live data will go here if it is added (step 7)
    fun getShoppingList(): List<RVShoppingItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingItem: RVShoppingItem)

    @Delete
    fun delete(shoppingItem: RVShoppingItem)

    @Update
    fun update(shoppingItem: RVShoppingItem)
}
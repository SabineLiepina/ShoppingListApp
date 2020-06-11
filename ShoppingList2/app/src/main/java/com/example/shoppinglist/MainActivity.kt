package com.example.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var shoppingMainList = arrayListOf<RVShoppingItem>()
    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecyclerAdapter()
        shoppingListRecView.adapter = adapter
        shoppingListRecView.layoutManager = LinearLayoutManager(this)


        //Button and text stuff
        val addItemButton: Button = findViewById(R.id.addItemButton)

        addItemButton.setOnClickListener {
            addNewItem()
        }

        //onlongclick = delete
        adapter.onLongClick = {

            //create an alert to go here to ask for deletion
            //for now it just removes the item
            Toast.makeText(this, "Long press has been activated", Toast.LENGTH_SHORT).show()
            shoppingMainList.remove(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun addNewItem() {

        val addItemShopList: EditText = findViewById(R.id.addItemEditText)

        var newItemText: String = addItemShopList.text.toString()

        if( newItemText.isNotEmpty() ){
//            Toast.makeText(this, "Added new item: $newItemText", Toast.LENGTH_SHORT).show()

            var newItem = RVShoppingItem()
            newItem.itemName = newItemText

            shoppingMainList.add(newItem)
            adapter.shoppingList = shoppingMainList.reversed()
            adapter.notifyDataSetChanged()

        } else {
            Toast.makeText(this, "Please enter an item!", Toast.LENGTH_SHORT).show()
        }

        addItemShopList.text.clear()
    }

}

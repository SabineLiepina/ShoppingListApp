package com.example.shoppinglist

import android.content.DialogInterface
import android.os.AsyncTask
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var shoppingMainList = arrayListOf<RVShoppingItem>()
    lateinit var adapter: RecyclerAdapter
    lateinit var alertDialogBuilder: AlertDialog.Builder
    private val shoppingListDAO by lazy {
        ShoppingListRoomDB.getDatabase(this).shoppingListDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Recycler view setup
        adapter = RecyclerAdapter()
        shoppingListRecView.adapter = adapter
        shoppingListRecView.layoutManager = LinearLayoutManager(this)

        //creates alert dialogue
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Delete item?")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

        //sets a cancel button
        alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }


        //'Add' button
        val addItemButton: Button = findViewById(R.id.addItemButton)

        //if 'Add' is pressed it adds the item to the list
        addItemButton.setOnClickListener {
            addNewItem()
        }

        getShoppingList()

        //onlongclick = delete
        adapter.onLongClick = {

//            Toast.makeText(this, "Long press has been activated", Toast.LENGTH_SHORT).show()

            //This is not the best way to do this, but it creates a button that confirms deleting the item
            alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
                Toast.makeText(applicationContext,
                    "Removing item...", Toast.LENGTH_SHORT).show()

                //removes selected item
                AsyncTask.execute {
                    shoppingListDAO.delete(it)
                    getShoppingList()
                }

            }
            alertDialogBuilder.show()

        }

        adapter.onCheckChange = {
//            shoppingListDAO.update(it)
        }

        getShoppingList()

    }

    //adds a 'Sign in' button to hte top of the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)

        //I wanted it so that when this is pressed you would be able to go to the created 'Log in' screen
    }

    private fun addNewItem() {

        val addItemShopList: EditText = findViewById(R.id.addItemEditText)

        val newItemText: String = addItemShopList.text.toString()

        //adds the item only if something is written in the 'edit text' field
        if( newItemText.isNotEmpty() ){
//            Toast.makeText(this, "Added new item: $newItemText", Toast.LENGTH_SHORT).show()

            val newItem = RVShoppingItem(itemName = newItemText, itemGot = false)
            AsyncTask.execute {
                shoppingListDAO.insert(newItem)
                getShoppingList()
            }

        } else {
            Toast.makeText(this, "Please enter an item!", Toast.LENGTH_SHORT).show()
        }

        //clears text field
        addItemShopList.text.clear()
    }

    private fun getShoppingList() {
        AsyncTask.execute {
            val list = shoppingListDAO.getShoppingList()
            runOnUiThread {
                adapter.shoppingList = list.reversed()
                adapter.notifyDataSetChanged()
            }
        }
    }
}

package com.example.userdatasharedpref

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var userInputText: EditText
    private lateinit var saveBtn: AppCompatButton
    private lateinit var showMessage: TextView
    private lateinit var sharedUserPref: SharedPreferences

    private lateinit var sharedUserPrefEditor: SharedPreferences.Editor
    private val preferenceData = R.string.user_data.toString()
    private val preferenceKey = R.string.user_data_key.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        userInputText = findViewById(R.id.message)
        saveBtn = findViewById(R.id.save)
        showMessage = findViewById(R.id.show_message)
        sharedUserPref = getSharedPreferences(preferenceData, MODE_PRIVATE)

        saveBtn.setOnClickListener {
            showUserPref()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserPref()
        userInputText.setText("")
    }
    private fun loadUserPref(){
        val message = sharedUserPref.getString(preferenceKey, "No data found")
        showMessage.text = message
    }

    private fun showUserPref(){
        val message = userInputText.text.toString()
        sharedUserPrefEditor = sharedUserPref.edit().also {
            it.putString(preferenceKey, message)
            it.apply()
            if(it.commit()){
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
                showMessage.text = message
            }
            else{
                Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
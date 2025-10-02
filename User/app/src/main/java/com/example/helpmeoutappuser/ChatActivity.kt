package com.example.helpmeoutappuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back)

        val eTPrompt= findViewById<EditText>(R.id.eTPrompt)
        val btnSubmit= findViewById<Button>(R.id.btnSubmit)
        val tVResult= findViewById<TextView>(R.id.tVResult)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnSubmit.setOnClickListener {
            val prompt= eTPrompt.text.toString()
            progressBar.visibility = View.VISIBLE

            val generativeModel = GenerativeModel(
                // For text-only input, use the gemini-pro model
                modelName = "gemini-1.5-pro",
                apiKey = "YOUR_API_KEY"
                // ENTER YOUR KEY
            )
            // Run network call on background thread using coroutines
            GlobalScope.launch(Dispatchers.IO) {
                val response = generativeModel.generateContent(Identity.identityPrompt + prompt)

                // Switch to the main thread to update UI
                withContext(Dispatchers.Main) {
                    // Hide the ProgressBar when the response is ready
                    progressBar.visibility = View.GONE

                    // Display the result
                    tVResult.text = response.text
                }
            }

        }

    }

    // Handle the back button click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle back button click
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
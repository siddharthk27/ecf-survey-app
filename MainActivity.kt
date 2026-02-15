package com.research.usagetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class MainActivity : AppCompatActivity() {
    
    private lateinit var nameInput: EditText
    private lateinit var startButton: Button
    private lateinit var prefs: AppPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        prefs = AppPreferences(this)
        
        // Check if user is already registered
        if (prefs.isRegistered()) {
            navigateToDashboard()
            return
        }
        
        setContentView(R.layout.activity_main)
        
        nameInput = findViewById(R.id.nameInput)
        startButton = findViewById(R.id.startButton)
        
        startButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Generate anonymous ID
            val anonymousId = UUID.randomUUID().toString().substring(0, 8)
            
            // Save user info
            prefs.setUserInfo(name, anonymousId)
            
            // Navigate to permission setup
            startActivity(Intent(this, PermissionSetupActivity::class.java))
            finish()
        }
    }
    
    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}

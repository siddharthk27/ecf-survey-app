package com.research.usagetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            
            // Disable button and show loading
            startButton.isEnabled = false
            startButton.text = "Registering..."
            
            // Generate anonymous ID
            val anonymousId = UUID.randomUUID().toString().substring(0, 8)
            
            // Register with Firebase backend
            CoroutineScope(Dispatchers.Main).launch {
                val participantToken = withContext(Dispatchers.IO) {
                    FirebaseSync.registerParticipant(this@MainActivity, name, anonymousId)
                }
                
                if (participantToken != null) {
                    // Save user info with token
                    prefs.setUserInfo(name, anonymousId, participantToken)
                    
                    // Navigate to permission setup
                    startActivity(Intent(this@MainActivity, PermissionSetupActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Registration failed. Please check your internet connection.",
                        Toast.LENGTH_LONG
                    ).show()
                    startButton.isEnabled = true
                    startButton.text = "Start Study"
                }
            }
        }
    }
    
    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}

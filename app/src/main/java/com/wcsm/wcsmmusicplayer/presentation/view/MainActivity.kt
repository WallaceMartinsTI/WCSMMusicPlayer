package com.wcsm.wcsmmusicplayer.presentation.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var userAlreadyHasPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkUserPermissions()

        // Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Checks if this is the first time the user has opened the app
            val intent = if(!isFirstTimeOpeningApp(this) && userAlreadyHasPermission) {
                Intent(this, MusicsActivity::class.java)
            } else {
                saveAppOpened(this)
                Intent(this, WelcomeActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 1500)
    }

    private fun checkUserPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            userAlreadyHasPermission = true
        }
    }

    private fun isFirstTimeOpeningApp(context: Context) : Boolean {
        val sharedPreferences = context.getSharedPreferences("WCSMMediaPlayerPublic", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstTime", true)
    }

    private fun saveAppOpened(context: Context) {
        val sharedPreferences = context.getSharedPreferences("WCSMMediaPlayerPublic", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTime", false)
        editor.apply()
    }
}
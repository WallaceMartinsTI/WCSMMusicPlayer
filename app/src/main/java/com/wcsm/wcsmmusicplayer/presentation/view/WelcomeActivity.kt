package com.wcsm.wcsmmusicplayer.presentation.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGetPermission.setOnClickListener {
            checkPermissions()
        }

        binding.btnGoToApp.setOnClickListener {
            val intent = Intent(this, MusicsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 200)
        } else {
            grantAccess()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            grantAccess()
        } else {
            showPermissionDialog()
        }
    }

    private fun showPermissionDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("AVISO DE PERMISSÃO")
            .setMessage("Para conseguir buscar as músicas do seu aparelho, você deve aceitar a permissão.")
            .setNegativeButton("SAIR DO APP") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setPositiveButton("ENTENDI") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        alertDialog.show()
    }

    private fun grantAccess() {
        binding.btnGoToApp.isEnabled = true
        binding.btnGetPermission.isEnabled = false
        binding.btnGetPermission.text = "ACESSO LIBERADO!"
    }
}
package com.hawkins.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.permissionx.hawkins.PermissionX

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            PermissionX.request(this, Manifest.permission.CALL_PHONE) {allGranted,deniedList ->
                if (allGranted) {
                    call()
                } else{
                    Toast.makeText(this,"You denied $deniedList",Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun call() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:10086")
        startActivity(intent)
    }
}
package com.example.finapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonSair = findViewById<Button>(R.id.buttonSair)
        buttonSair.setOnClickListener {
            finishAffinity()
        }

        val buttonCadastro = findViewById<Button>(R.id.buttonCadastro)
        buttonCadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        val buttonExtrato = findViewById<Button>(R.id.buttonExtrato)
        buttonExtrato.setOnClickListener {
            val intent = Intent(this, Extrato::class.java)
            startActivity(intent)
        }
    }
}
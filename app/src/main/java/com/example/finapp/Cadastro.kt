package com.example.finapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cadastro : AppCompatActivity() {

    private lateinit var editDescricao: EditText
    private lateinit var editValor: EditText
    private lateinit var radioGroupTipo: RadioGroup
    private lateinit var buttonCadastrar: Button
    private lateinit var buttonVoltar: Button
    private lateinit var dbHelper: TransacaoDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializações
        editDescricao = findViewById(R.id.editDescricao)
        editValor = findViewById(R.id.editValor)
        radioGroupTipo = findViewById(R.id.radioGroupTipo)
        buttonCadastrar = findViewById(R.id.buttonCadastrar)
        buttonVoltar = findViewById(R.id.buttonVoltar)
        dbHelper = TransacaoDBHelper(this)

        buttonCadastrar.setOnClickListener {
            val descricao = editDescricao.text.toString()
            val valorText = editValor.text.toString()
            val tipoSelecionadoId = radioGroupTipo.checkedRadioButtonId

            // Validações simples
            if (descricao.isBlank() || valorText.isBlank() || tipoSelecionadoId == -1) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val valor = valorText.toDoubleOrNull()
            if (valor == null || valor <= 0) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipo = when (tipoSelecionadoId) {
                R.id.radioCredito -> "Crédito"
                R.id.radioDebito -> "Débito"
                else -> ""
            }

            val transacao = Transacao(descricao = descricao, valor = valor, tipo = tipo)
            dbHelper.inserirTransacao(transacao)

            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

            // Limpa os campos
            editDescricao.text.clear()
            editValor.text.clear()
            radioGroupTipo.clearCheck()
        }

        buttonVoltar.setOnClickListener {
            finish() // Apenas finaliza a activity, voltando à anterior
        }
    }
}

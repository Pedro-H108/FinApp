package com.example.finapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Extrato : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var textSaldo: TextView
    private lateinit var radioFiltro: RadioGroup
    private lateinit var dbHelper: TransacaoDBHelper
    private lateinit var todasTransacoes: List<Transacao>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)

        listView = findViewById(R.id.listTransacoes)
        textSaldo = findViewById(R.id.textSaldo)
        radioFiltro = findViewById(R.id.radioFiltro)
        dbHelper = TransacaoDBHelper(this)

        carregarTransacoes()

        radioFiltro.setOnCheckedChangeListener { _, _ ->
            exibirTransacoesFiltradas()
        }

        val buttonVoltar = findViewById<Button>(R.id.buttonVoltar)
        buttonVoltar.setOnClickListener {
            finish()
        }
    }

    private fun carregarTransacoes() {
        todasTransacoes = dbHelper.listarTodas()
        exibirTransacoesFiltradas()
    }

    private fun exibirTransacoesFiltradas() {
        val selecionado = when (radioFiltro.checkedRadioButtonId) {
            R.id.radioCreditos -> "Crédito"
            R.id.radioDebitos -> "Débito"
            else -> null
        }

        val filtradas = if (selecionado == null) {
            todasTransacoes
        } else {
            todasTransacoes.filter { it.tipo == selecionado }
        }

        listView.adapter = TransacaoAdapter(this, filtradas)

        val saldo = todasTransacoes.sumOf {
            if (it.tipo == "Crédito") it.valor else -it.valor
        }

        textSaldo.text = "Saldo: R$ %.2f".format(saldo)
    }


}

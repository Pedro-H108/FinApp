package com.example.finapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TransacaoDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "finapp.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "transacoes"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRICAO = "descricao"
        const val COLUMN_VALOR = "valor"
        const val COLUMN_TIPO = "tipo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRICAO TEXT,
                $COLUMN_VALOR REAL,
                $COLUMN_TIPO TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserirTransacao(transacao: Transacao): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESCRICAO, transacao.descricao)
            put(COLUMN_VALOR, transacao.valor)
            put(COLUMN_TIPO, transacao.tipo)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun listarTodas(): List<Transacao> {
        val lista = mutableListOf<Transacao>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALOR))
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO))
                lista.add(Transacao(id, descricao, valor, tipo))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

}

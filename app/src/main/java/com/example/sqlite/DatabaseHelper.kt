// Archivo: DatabaseHelper.kt
package com.example.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Importamos UserEntry desde DatabaseContract para usar sus constantes
import com.example.sqlite.DatabaseContract.UserEntry

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseContract.DATABASE_NAME,
    null,
    DatabaseContract.DATABASE_VERSION
) {

    private val TAG = "DatabaseHelper"

    // Sentencia SQL para crear la tabla
    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
                "${UserEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${UserEntry.COLUMN_NAME} TEXT," +
                "${UserEntry.COLUMN_AGE} INTEGER)"

    override fun onCreate(db: SQLiteDatabase) {
        // Se llama la primera vez que se accede a la BD. Crea la tabla.
        Log.d(TAG, "Creando tabla: $SQL_CREATE_ENTRIES")
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Lógica de actualización: borrar y recrear la tabla.
        Log.d(TAG, "Actualizando BD de $oldVersion a $newVersion")
        db.execSQL("DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}")
        onCreate(db)
    }
}
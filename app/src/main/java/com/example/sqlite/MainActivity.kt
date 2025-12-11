package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log

// Importaciones de tus clases (Asegúrate que el paquete sea correcto: com.example.sqlite)
import com.example.sqlite.DatabaseContract.UserEntry
import com.example.sqlite.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    // Inicialización de la clase que gestiona la BD
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el ayudante de la BD
        dbHelper = DatabaseHelper(this)


        // --- Ejecutar las operaciones de prueba ---

        Log.d(TAG, "--- INICIO DE PRUEBAS SQLite ---")

        // Insertar un nuevo usuario
        val newRowId1 = insertData("Alice", 25)
        Log.i(TAG, "Resultado Insertar Alice (ID): $newRowId1")

        // Insertar otro usuario
        val newRowId2 = insertData("Bob", 30)
        Log.i(TAG, "Resultado Insertar Bob (ID): $newRowId2")

        // Seleccionar y mostrar todos los datos
        readData()

        // Actualizar un usuario
        val rowsUpdated = updateData(newRowId1, 26)
        Log.i(TAG, "Filas actualizadas para Alice: $rowsUpdated")

        // Seleccionar y mostrar todos los datos de nuevo
        readData()

        // Eliminar un usuario
        val rowsDeleted = deleteData(newRowId2)
        Log.i(TAG, "Filas eliminadas (Bob): $rowsDeleted")

        // Seleccionar y mostrar el resultado final
        readData()

        Log.d(TAG, "--- FIN DE PRUEBAS SQLite ---")
    }

    /**
     * Inserta un nuevo registro en la tabla 'users'.
     * Utiliza ContentValues para mapear las columnas a los valores.
     * @return El ID de la nueva fila.
     */
    private fun insertData(name: String, age: Int): Long {
        // use{} asegura que la BD se cierra automáticamente después de la operación
        return dbHelper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(UserEntry.COLUMN_NAME, name)
                put(UserEntry.COLUMN_AGE, age)
            }
            // Inserta la nueva fila
            db.insert(UserEntry.TABLE_NAME, null, values)
        }
    }

    /**
     * Actualiza la edad de un usuario específico.
     * @return El número de filas afectadas.
     */
    private fun updateData(userId: Long, newAge: Int): Int {
        return dbHelper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(UserEntry.COLUMN_AGE, newAge)
            }

            // Define la cláusula WHERE y los argumentos
            val selection = "${UserEntry.COLUMN_ID} = ?"
            val selectionArgs = arrayOf(userId.toString())

            // Actualiza las filas
            db.update(
                UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    /**
     * Elimina un usuario específico por su ID.
     * @return El número de filas eliminadas.
     */
    private fun deleteData(userId: Long): Int {
        return dbHelper.writableDatabase.use { db ->
            // Define la cláusula WHERE y los argumentos
            val selection = "${UserEntry.COLUMN_ID} = ?"
            val selectionArgs = arrayOf(userId.toString())

            // Elimina las filas
            db.delete(
                UserEntry.TABLE_NAME,
                selection,
                selectionArgs
            )
        }
    }

    /**
     * Selecciona todos los registros de la tabla 'users' y los muestra por Logcat.
     */
    private fun readData() {
        // use{} también se utiliza para la BD de lectura
        dbHelper.readableDatabase.use { db ->
            val projection = arrayOf(UserEntry.COLUMN_ID, UserEntry.COLUMN_NAME, UserEntry.COLUMN_AGE)

            // Realiza la consulta
            val cursor = db.query(
                UserEntry.TABLE_NAME,
                projection,
                null, null, null, null, null // Sin filtros, grupos ni orden
            )

            Log.d(TAG, "--- INICIO DE DATOS DE USUARIOS ---")
            // use{} en el Cursor asegura que se cierra
            cursor.use { c ->
                // Itera sobre los resultados
                while (c.moveToNext()) {
                    // Recupera valores de forma segura
                    val itemId = c.getLong(c.getColumnIndexOrThrow(UserEntry.COLUMN_ID))
                    val name = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME))
                    val age = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_AGE))

                    Log.i(TAG, "ID: $itemId, Nombre: $name, Edad: $age")
                }
            }
            Log.d(TAG, "--- FIN DE DATOS DE USUARIOS ---")
        }
    }
}
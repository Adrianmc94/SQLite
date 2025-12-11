package com.example.sqlite

object DatabaseContract {
    // Constantes de la base de datos
    const val DATABASE_NAME = "MySimpleDatabase.db"
    const val DATABASE_VERSION = 1

    // Objeto interno para la tabla de ejemplo (Usuarios)
    object UserEntry {
        const val TABLE_NAME = "users"
        // Columna ID (Primary Key)
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
    }
}
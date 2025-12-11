# Proyecto de Gestión de Usuarios con SQLite

Este proyecto es una implementación básica de la gestión de una base de datos local (SQLite) utilizando Kotlin, enfocada en demostrar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar).

El código sigue las estructuras utilizando `SQLiteOpenHelper` para la gestión de la base de datos.

## Estructura del Proyecto

El proyecto separa responsabilidades en tres archivos principales:

* **`DatabaseContract.kt`**: Define las constantes necesarias para la base de datos, incluyendo el nombre de la BD, la versión y los nombres de la tabla (`users`) y sus columnas (`_id`, `name`, `age`).
* **`DatabaseHelper.kt`**: Extiende `SQLiteOpenHelper`. Se encarga de crear la tabla (`onCreate`) y de manejar las actualizaciones de la versión de la base de datos (`onUpgrade`).
* **`MainActivity.kt`**: Contiene la lógica de prueba para ejecutar las operaciones CRUD: inserción, selección, actualización y eliminación de datos.

## Operaciones Implementadas (CRUD)

La clase `MainActivity.kt` incluye los siguientes métodos para interactuar con la base de datos:

### Insertar Datos (Create)

* **Método:** `insertData(name: String, age: Int)`
* **Detalle:** Utiliza `dbHelper.writableDatabase` y `ContentValues` para insertar un nuevo registro en la tabla `users`.

### Seleccionar Datos (Read)

* **Método:** `readData()`
* **Detalle:** Realiza una consulta completa (`db.query`) y utiliza un `Cursor` para iterar sobre los resultados, imprimiéndolos en Logcat.

### Actualizar Datos (Update)

* **Método:** `updateData(userId: Long, newAge: Int)`
* **Detalle:** Actualiza la columna `age` de un registro específico, utilizando la columna `_id` en la cláusula `WHERE` para filtrar.

### Eliminar Datos (Delete)

* **Método:** `deleteData(userId: Long)`
* **Detalle:** Elimina un registro de la tabla identificándolo por su `_id` mediante la cláusula `WHERE`.

## Nota sobre el Manejo de Recursos

Todas las operaciones de base de datos en `MainActivity.kt` utilizan la función de extensión `use { }` de Kotlin. Esto asegura que tanto las instancias de `SQLiteDatabase` como el `Cursor` se cierren automáticamente, garantizando una gestión eficiente de los recursos y previniendo fugas de memoria.

## Resultados

La ejecución de las pruebas se puede verificar en el Logcat, donde se muestran los resultados de cada operación (IDs de inserción, filas actualizadas/eliminadas y el estado de la tabla después de cada cambio).

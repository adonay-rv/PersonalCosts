package com.example.personalcosts;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MoneyDB extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "Money_DataBase.db";

    // Versión de la base de datos. Si cambias la estructura de la base de datos, debes incrementar la versión.
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla de usuarios
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PASSWORD = "password";

    // Nombre de la tabla de Presupuesto
    public static final String TABLE_PRESUPUESTO = "presupuesto";
    public static final String COLUMN_PRESUPUESTO_ID = "presupuesto_id";
    public static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_PRESUPUESTO = "presupuesto";
    public static final String COLUMN_FECHA_INICIO = "fecha_inicio";
    public static final String COLUMN_FECHA_CORTE = "fecha_corte";
    public static final String COLUMN_PRESUPUESTO_ACUMULADO = "presupuesto_acumulado";


    // Nombre de la tabla de Categorias
    public static final String TABLE_CATEGORIA = "categoria";
    public static final String COLUMN_CATEGORIA_ID = "categoria_id";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_DESCRIPCION_CATEGORIA = "descripcion_categoria";

    // Nombre de la tabla de Gastos
    public static final String TABLE_GASTO = "gasto";
    public static final String COLUMN_GASTO_ID = "gasto_id";
    public static final String COLUMN_GASTO = "gasto";
    public static final String COLUMN_CATEGORIA_ID_FK = "categoria_id";
    public static final String COLUMN_DESCRIPCION_GASTO = "descripcion_gasto";

    // Constructor
    public MoneyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla de usuarios
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Crea la tabla de presupuesto
        String CREATE_PRESUPUESTO_TABLE = "CREATE TABLE " + TABLE_PRESUPUESTO + "("
                + COLUMN_PRESUPUESTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + COLUMN_PRESUPUESTO + " REAL,"  // REAL para valores decimales
                + COLUMN_FECHA_INICIO + " TEXT,"
                + COLUMN_FECHA_CORTE + " TEXT,"
                + COLUMN_PRESUPUESTO_ACUMULADO + " REAL" + ")";
        db.execSQL(CREATE_PRESUPUESTO_TABLE);

        // Crea la tabla de categorías
        String CREATE_CATEGORIA_TABLE = "CREATE TABLE " + TABLE_CATEGORIA + "("
                + COLUMN_CATEGORIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CATEGORIA + " TEXT,"
                + COLUMN_DESCRIPCION_CATEGORIA + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORIA_TABLE);

        // Crea la tabla de gastos
        String CREATE_GASTO_TABLE = "CREATE TABLE " + TABLE_GASTO + "("
                + COLUMN_GASTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_GASTO + " TEXT,"
                + COLUMN_CATEGORIA_ID_FK + " INTEGER,"  // Referencia a la tabla de categorías
                + COLUMN_DESCRIPCION_GASTO + " TEXT" + ")";
        db.execSQL(CREATE_GASTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si cambias la versión de la base de datos, puedes realizar aquí las actualizaciones necesarias.
        // Por ejemplo, puedes eliminar y recrear las tablas.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESUPUESTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTO);
        onCreate(db);
    }


// abajo estaran los metodos para hacer el crud y otras funciones

    //Guardar Usuario
    public long saveUser(String email, String firstName, String lastName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1; // Valor predeterminado en caso de error

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_FIRST_NAME, firstName);
            values.put(COLUMN_LAST_NAME, lastName);
            values.put(COLUMN_PASSWORD, password);

            newRowId = db.insertOrThrow(TABLE_USERS, null, values);
        } catch (SQLiteException e) {
            // Manejar el error de inserción, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    //Borrar Usuario
    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});

            // Verificar si se eliminó al menos una fila
            if (rowsDeleted > 0) {
                return true; // el usuario se eliminó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo eliminar al usuario
    }


    //Editar Usuario
    public boolean updateUser(int userId, String newEmail, String newFirstName, String newLastName, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, newEmail);
            values.put(COLUMN_FIRST_NAME, newFirstName);
            values.put(COLUMN_LAST_NAME, newLastName);
            values.put(COLUMN_PASSWORD, newPassword);

            int rowsUpdated = db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});

            // Verificar si se actualizó al menos una fila
            if (rowsUpdated > 0) {
                return true; // Éxito: el usuario se actualizó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo actualizar el usuario
    }



    //Guardar Presupuesto
    public long savePresupuesto(int userId, double presupuesto, String fechaInicio, String fechaCorte, double presupuestoAcumulado) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1; // Valor predeterminado en caso de error

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID_FK, userId);
            values.put(COLUMN_PRESUPUESTO, presupuesto);
            values.put(COLUMN_FECHA_INICIO, fechaInicio);
            values.put(COLUMN_FECHA_CORTE, fechaCorte);
            values.put(COLUMN_PRESUPUESTO_ACUMULADO, presupuestoAcumulado);

            newRowId = db.insertOrThrow(TABLE_PRESUPUESTO, null, values);
        } catch (SQLiteException e) {
            // Manejar el error de inserción, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    //borrar presupuesto
    public boolean deletePresupuesto(int presupuestoId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(TABLE_PRESUPUESTO, COLUMN_PRESUPUESTO_ID + " = ?", new String[]{String.valueOf(presupuestoId)});

            // Verificar si se eliminó al menos una fila
            if (rowsDeleted > 0) {
                return true; // Éxito: el presupuesto se eliminó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo eliminar el presupuesto
    }

    //Editar presupuesto
    public boolean updatePresupuesto(int presupuestoId, double newPresupuesto, String newFechaInicio, String newFechaCorte, double newPresupuestoAcumulado) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRESUPUESTO, newPresupuesto);
            values.put(COLUMN_FECHA_INICIO, newFechaInicio);
            values.put(COLUMN_FECHA_CORTE, newFechaCorte);
            values.put(COLUMN_PRESUPUESTO_ACUMULADO, newPresupuestoAcumulado);

            int rowsUpdated = db.update(TABLE_PRESUPUESTO, values, COLUMN_PRESUPUESTO_ID + " = ?", new String[]{String.valueOf(presupuestoId)});

            // Verificar si se actualizó al menos una fila
            if (rowsUpdated > 0) {
                return true; // Éxito: el presupuesto se actualizó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo actualizar el presupuesto
    }


    //Guardar Categoria
    public long saveCategoria(String nombreCategoria, String descripcionCategoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1; // Valor predeterminado en caso de error

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORIA, nombreCategoria);
            values.put(COLUMN_DESCRIPCION_CATEGORIA, descripcionCategoria);

            newRowId = db.insertOrThrow(TABLE_CATEGORIA, null, values);
        } catch (SQLiteException e) {
            // Manejar el error de inserción, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    //borrar categoria
    public boolean deleteCategoria(int categoriaId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(TABLE_CATEGORIA, COLUMN_CATEGORIA_ID + " = ?", new String[]{String.valueOf(categoriaId)});

            // Verificar si se eliminó al menos una fila
            if (rowsDeleted > 0) {
                return true; // Éxito: la categoría se eliminó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo eliminar la categoría
    }

    //Editar Categoria
    public boolean updateCategoria(int categoriaId, String newNombreCategoria, String newDescripcionCategoria) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORIA, newNombreCategoria);
            values.put(COLUMN_DESCRIPCION_CATEGORIA, newDescripcionCategoria);

            int rowsUpdated = db.update(TABLE_CATEGORIA, values, COLUMN_CATEGORIA_ID + " = ?", new String[]{String.valueOf(categoriaId)});

            // Verificar si se actualizó al menos una fila
            if (rowsUpdated > 0) {
                return true; // Éxito: la categoría se actualizó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo actualizar la categoría
    }

    //guardar Gasto
    public long saveGasto(String nombreGasto, int categoriaId, String descripcionGasto) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1; // Valor predeterminado en caso de error

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_GASTO, nombreGasto);
            values.put(COLUMN_CATEGORIA_ID_FK, categoriaId);
            values.put(COLUMN_DESCRIPCION_GASTO, descripcionGasto);

            newRowId = db.insertOrThrow(TABLE_GASTO, null, values);
        } catch (SQLiteException e) {
            // Manejar el error de inserción, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    //borrar gasto
    public boolean deleteGasto(int gastoId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(TABLE_GASTO, COLUMN_GASTO_ID + " = ?", new String[]{String.valueOf(gastoId)});

            // Verificar si se eliminó al menos una fila
            if (rowsDeleted > 0) {
                return true; // Éxito: el gasto se eliminó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo eliminar el gasto
    }

    //Editar Gasto
    public boolean updateGasto(int gastoId, String newNombreGasto, int newCategoriaId, String newDescripcionGasto) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_GASTO, newNombreGasto);
            values.put(COLUMN_CATEGORIA_ID_FK, newCategoriaId);
            values.put(COLUMN_DESCRIPCION_GASTO, newDescripcionGasto);

            int rowsUpdated = db.update(TABLE_GASTO, values, COLUMN_GASTO_ID + " = ?", new String[]{String.valueOf(gastoId)});

            // Verificar si se actualizó al menos una fila
            if (rowsUpdated > 0) {
                return true; // Éxito: el gasto se actualizó correctamente
            }
        } catch (SQLiteException e) {
            // Manejar el error, por ejemplo, registrando el error o lanzando una excepción personalizada.
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false; // Fallo: no se pudo actualizar el gasto
    }



}
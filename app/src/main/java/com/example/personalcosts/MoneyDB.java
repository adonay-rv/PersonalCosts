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



}
package com.example.aplicacion_tienda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

public class dbUsuarios extends dbHelper{

    Context context;

    public dbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertaUsuario(String email, String password){

        long id = 0;

        try {
            dbHelper dbHelper = new dbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("password", password);

            id = db.insert("t_usuarios", null, values);
        } catch (Exception ex){
            ex.toString();
            Log.e("DB_ERROR", "Error al insertar usuario", ex);
        }
        return id;
    }
}

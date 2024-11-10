package com.example.aplicacion_tienda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ApliacionTienda.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTOS = "Lista_Productos";
    private static final String COLUMN_PRODUCTOS_ID = "productos_id";
    private static final String COLUMN_PRODUCTOS_NOMBRE = "Nombre";
    private static final String COLUMN_PRODUCTOS_PRECIO = "Precio";
    private static final String COLUMN_PRODUCTOS_CANTIDAD = "Cantidad";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTOS +
                        " (" + COLUMN_PRODUCTOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRODUCTOS_NOMBRE + " TEXT, " +
                        COLUMN_PRODUCTOS_PRECIO + " INTEGER, " +
                        COLUMN_PRODUCTOS_CANTIDAD + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Elimina la tabla si exite y crea una nueva en caso de que se actualice
        //Tambien hay que agregar la otra tabla aqui, los otros metodos son unicos para cada tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }


    //Usamos el metodo getwrite en una base de datos, para agregar cosas
    //Este metodo lo traemos de la extencion SQLiteOpenHelper
    void agregarProductos(String nombre, int precio, int cantidad){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put( COLUMN_PRODUCTOS_NOMBRE, nombre);
        cv.put( COLUMN_PRODUCTOS_PRECIO, precio);
        cv.put( COLUMN_PRODUCTOS_CANTIDAD, cantidad);
        long result = db.insert(TABLE_PRODUCTOS,null, cv);

        if (result == -1){
            Toast.makeText(context, "No se pudo añadir", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Se añadio con exito", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para mostrar la información de la base en el recyclerView
    Cursor mostrarAllData(){
        String query = "SELECT * FROM " + TABLE_PRODUCTOS;
        SQLiteDatabase db = this.getReadableDatabase();

        //Lee toda la informacion de la base para mostrarla en la lista de productos
        Cursor cursor = null;
        if (db != null){
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Metodo para actualizar los datos
    void actualizarDatosProducto( String row_id, String nombre, String precio, String cantidad){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCTOS_NOMBRE, nombre);
        cv.put(COLUMN_PRODUCTOS_PRECIO, precio);
        cv.put(COLUMN_PRODUCTOS_CANTIDAD, cantidad);

        long result = db.update(TABLE_PRODUCTOS,cv,"productos_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Se actualizo con exito", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para eliminar datos, ¡por fin! casi que no llego
    void eliminarProducto (String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_PRODUCTOS, "productos_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Se elimino con exito", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para borrar todos los productos usando el item del menu
    void eliminarTodo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTOS);
    }
}

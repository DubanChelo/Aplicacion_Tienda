package com.example.aplicacion_tienda;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton BotonAgregar;

    MyDatabaseHelper myDB;
    ArrayList<String> productos_id, productos_nombre, productos_precio, productos_cantidad;
    AdatadorListaProductos adatadorLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);

        //Busca por id el recyclerview, el boton agregar y a futuro el boton carrito.
        recyclerView = findViewById(R.id.recyclerview);
        BotonAgregar = findViewById(R.id.BotonAgregar);
        //Se crea un boton para que redirija a la actividad Agregar Productos
        BotonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProductos.this, AgregarProductos.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(ListaProductos.this);
        productos_id = new ArrayList<>();
        productos_nombre = new ArrayList<>();
        productos_precio = new ArrayList<>();
        productos_cantidad = new ArrayList<>();


        mostrarDataArrays();


        //Inicializamos el la clase AdatadorListaProductos
        adatadorLista = new AdatadorListaProductos(ListaProductos.this,this, productos_id,
                productos_nombre, productos_precio, productos_cantidad);
        recyclerView.setAdapter(adatadorLista);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListaProductos.this));
    }

    //Metodo para refrescar la aplicacion, despues de usar el metodo actualizarDatosProducto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void mostrarDataArrays(){
        Cursor cursor = myDB.mostrarAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No hay información", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                productos_id.add(cursor.getString(0));
                productos_nombre.add(cursor.getString(1));
                productos_precio.add(cursor.getString(2));
                productos_cantidad.add(cursor.getString(3));
            }
        }
    }

    //Metodo para darle las fucniones a los items del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //metodo para hacer que cree un mensaje cuando se de click

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_eliminarTodo) {
            confirmarMensaje();
        }   else if (id == R.id.item_Carrito) {
            Toast.makeText(this, "Abriendo carrito", Toast.LENGTH_SHORT).show();
            Intent intentCarro = new Intent(this, Carrito.class);
            startActivity(intentCarro);
        }
        //else if (id == R.id.item_geolocalizacion) {
        //            Toast.makeText(this, "Abriendo geolocalización", Toast.LENGTH_SHORT).show();
        //            Intent intentGeo = new Intent(this, GeolocalizacionActivity.class);
        //            startActivity(intentGeo);
        return super.onOptionsItemSelected(item);
    }

    //Metodo para confimar la respuesta
    void confirmarMensaje(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Eliminar el todo?");
        alerta.setMessage("Estas seguro que quieres eliminar todos los productos ?");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ListaProductos.this);
                myDB.eliminarTodo();
                // Recarga la actividad para reflejar los cambios o por si se crea una pestaña negra
                Intent intent = new Intent(ListaProductos.this, ListaProductos.class);
                startActivity(intent);
                finish();
            }
        });
        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta.create().show();
    }

}

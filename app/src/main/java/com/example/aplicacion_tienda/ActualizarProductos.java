package com.example.aplicacion_tienda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActualizarProductos extends AppCompatActivity {

    EditText nombre_input, precio_input, cantidad_input;
    Button actualizarBoton, eliminarBoton;

    // Variables para almacenar los valores para actualizar el producto
    String id, nombre, precio, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_productos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombre_input = findViewById(R.id.nombre_input2);
        precio_input = findViewById(R.id.precio_input2);
        cantidad_input = findViewById(R.id.cantidad_input2);
        actualizarBoton = findViewById(R.id.actualizar_boton);
        eliminarBoton = findViewById(R.id.eliminar_boton);

        //Primero llamamos este metodo
        actualizarProducto();

        //Metodo para cambiar el nombre de la barra superior de forma automatica
        //segun la variable que escojamos
        ActionBar ab = getSupportActionBar();
        assert ab != null; //esto se crea para evitar que el codigo no funcione si no hay una barra
        ab.setTitle(nombre);

        actualizarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtén los valores actuales de los campos de texto
                nombre = nombre_input.getText().toString().trim();
                precio = precio_input.getText().toString().trim();
                cantidad = cantidad_input.getText().toString().trim();

                // Llama al método de actualización de la base de datos
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActualizarProductos.this);
                myDB.actualizarDatosProducto(id, nombre, precio, cantidad);

                Toast.makeText(ActualizarProductos.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            }
        });

        //evento click para eliminar
        eliminarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarMensaje();
            }
        });

    }

    void actualizarProducto() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("precio") && getIntent().hasExtra("cantidad")) {

            // Obtenemos los datos de los productos
            id = getIntent().getStringExtra("id");
            nombre = getIntent().getStringExtra("nombre");
            precio = getIntent().getStringExtra("precio");
            cantidad = getIntent().getStringExtra("cantidad");

            // Seteamos los datos en los inputs
            nombre_input.setText(nombre);
            precio_input.setText(precio);
            cantidad_input.setText(cantidad);

        } else {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para confimar la respuesta
    void confirmarMensaje(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Eliminar el producto " + nombre +" ?");
        alerta.setMessage("Estas seguro que quieres eliminar el producto " + nombre + " ?");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActualizarProductos.this);
                myDB.eliminarProducto(id);
                //Termina la actividad que esta haciendo y vuelve a la activida pricipal
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

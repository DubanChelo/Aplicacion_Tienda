package com.example.aplicacion_tienda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AgregarProductos extends AppCompatActivity {

    EditText nombre_input, precio_input, cantidad_input;
    Button añadir_boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_productos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombre_input = findViewById(R.id.nombre_input);
        precio_input = findViewById(R.id.precio_input);
        cantidad_input = findViewById(R.id.cantidad_input);

        añadir_boton = findViewById(R.id.añadir_boton);
        añadir_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MyDatabaseHelper myDB = new MyDatabaseHelper(AgregarProductos.this);
              myDB.agregarProductos(nombre_input.getText().toString().trim(),
                      Integer.parseInt(precio_input.getText().toString().trim()),
                      Integer.parseInt(cantidad_input.getText().toString().trim()));
            }
        });
    }
}
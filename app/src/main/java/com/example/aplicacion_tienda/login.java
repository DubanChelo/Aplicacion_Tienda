package com.example.aplicacion_tienda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.aplicacion_tienda.db.*;


public class login extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        // Creando la base de datos
        dbHelper dbhelper = new dbHelper(login.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        if (db != null){
            Toast.makeText(login.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(login.this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }

        // Codigo para eventos de cambios entre ventanas, del login al alRegister
        Button alRegister = findViewById(R.id.register);
        alRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, com.example.aplicacion_tienda.alRegister.class);
                startActivity(intent);
            }
        });
    }

}

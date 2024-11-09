package com.example.aplicacion_tienda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicacion_tienda.db.dbHelper;
import com.example.aplicacion_tienda.db.dbUsuarios;

public class alRegister extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_al_register);

        // Codigo para guardar la información en la base de datos
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        guardar = findViewById(R.id.toRegister);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbUsuarios dbusuarios = new dbUsuarios(alRegister.this);
                long id = dbusuarios.insertaUsuario(txtEmail.getText().toString(),txtPassword.getText().toString());

                if (id > 0){
                    Toast.makeText(alRegister.this, "REGISTRADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(alRegister.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    limpiar();
                }
            }
        });


        // Codigo para eventos de cambios entre ventanas, del alRegister al login
        Button alLogin = findViewById(R.id.toLogin);
        alLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alRegister.this, login.class);
                startActivity(intent);
            }
        });
    }

    private void limpiar (){
        txtEmail.setText("");
        txtPassword.setText("");
    }

}
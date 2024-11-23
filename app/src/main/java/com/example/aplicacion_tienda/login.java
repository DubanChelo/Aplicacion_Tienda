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
import androidx.activity.result.IntentSenderRequest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.aplicacion_tienda.db.*;

//importaciones de google
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

// Imports del video que no tenía
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class login extends AppCompatActivity {

    // Variables globales
    private TextView textView;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 100;

    // Metodo para crear la base de datos
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        // Codigo configurando el one Tap Client
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.google_cliente_auth))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();

        // Creando la base de datos
        dbHelper dbhelper = new dbHelper(login.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        if (db != null){
            Toast.makeText(login.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(login.this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }


        //Metodo para eventos de cambios entre ventanas, del login al alRegister
        Button alRegister = findViewById(R.id.register);
        alRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, com.example.aplicacion_tienda.alRegister.class);
                startActivity(intent);
            }
        });
        Button botonLogin = findViewById(R.id.login);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, ListaProductos.class);
                startActivity(intent);
            }
        });
    }

    public void buttonGoogleSignIn(View view) {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
            @Override
            public void onSuccess(BeginSignInResult result) {
                try {
                    startIntentSenderForResult(
                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                            null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                }
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();

                    Toast.makeText(login.this, "Autenticación correcta " + username, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(login.this, ListaProductos.class);
                    startActivity(intent);

                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.");
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.");
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "Error en el inicio de sesión: " + e.getMessage(), e);
                }
                break;
        }
    }
}
package com.yeltsin.crudretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yeltsin.crudretrofit.LoginActivity;
import com.yeltsin.crudretrofit.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText edtUsuario, edtCorreo, edtContraseña;
    private Button btnBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        edtUsuario = findViewById(R.id.edtUsuario);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);
        btnBoton = findViewById(R.id.btnBoton);

        // Configurar el listener del botón
        btnBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados
                String usuario = edtUsuario.getText().toString();
                String correo = edtCorreo.getText().toString();
                String contraseña = edtContraseña.getText().toString();

                // Validar que todos los campos estén completos
                if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contraseña)) {
                    Toast.makeText(RegistroActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    // Validar formato de correo electrónico
                    Toast.makeText(RegistroActivity.this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
                } else if (contraseña.length() < 8) {
                    // Validar longitud mínima de la contraseña
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    // Todo está correcto, puedes redirigir a LoginActivity
                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
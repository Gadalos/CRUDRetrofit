package com.yeltsin.crudretrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends AppCompatActivity {

        private Button botonAcceso;
        private EditText usuario;
        private EditText contraseña;

        //usuario y contraseña predefinidos
        private static final String USUARIO_PREDEFINIDO = "cielo@gmail.com";
        private static final String CONTRASENA_PREDEFINIDA = "123456";

        // inicializando variable de intentos fallidos
        private int intentosFallidos = 0;

        // delimitando el maximo de intentos fallidos a 3
        private static final int MAX_INTENTOS_FALLIDOS = 3;

        // Estableciendo el tiempo de bloqueo despues que se fallaron los 3 intentos
        private static final long BLOQUEO_TIEMPO_MS = 30 * 1000; // 30 segundos

        // establece si el acceso esta bloqueado
        private boolean bloqueado = false;

        //alamcena el tiempo para saber en que momento desbloquear el acceso
        private long tiempoDesbloqueo = 0;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            // se definen los elementos de la actividad y se busca su id en el xml

            botonAcceso = findViewById(R.id.btnBoton);
            usuario = findViewById(R.id.edtUsuario);
            contraseña = findViewById(R.id.edtContraseña);

            // establece que al hacer clic al botonAcceso se llame al metodo ingreso

            botonAcceso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingreso();
                }
            });
        }

        // se crea un metodo ingreso y verifica si al acceso esta bloqueado o no.
        //si esta bloqueado muestra un mensaje.
        private void ingreso() {
            if (bloqueado && System.currentTimeMillis() < tiempoDesbloqueo) {
                Toast.makeText(this, "Sesión bloqueada. Espere un momento.", Toast.LENGTH_SHORT).show();
                return;
            }

            // se obtienen los valores ingresados en los campos de texto

            String usuarioInput = usuario.getText().toString();
            String contraseñaInput = contraseña.getText().toString();

            // verifica si se ha ingresado tanto el usuario como la contraseña
            if (usuarioInput.isEmpty() || contraseñaInput.isEmpty()) {
                Toast.makeText(this, "Ingrese tanto el usuario como la contraseña", Toast.LENGTH_SHORT).show();
            } else {
                //comprueba si las credenciales ingresadas son iguales a las predefinidas.
                if (usuarioInput.equals(USUARIO_PREDEFINIDO) && contraseñaInput.equals(CONTRASENA_PREDEFINIDA)) {
                    // redirigir al usuario al token activity.
                    Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                    intent.putExtra("usuario", usuarioInput);
                    intent.putExtra("contraseña", contraseñaInput);
                    startActivity(intent);
                } else {

                    // Credenciales incorrectas, incrementa el contador de intentos fallidos.
                    intentosFallidos++;
                    Toast.makeText(this, "Credenciales incorrectas. Intentos fallidos: " + intentosFallidos, Toast.LENGTH_SHORT).show();
                    // si e numero de intentos falidos alcanza el maximo llama al metodo bloquear acceso
                    if (intentosFallidos >= MAX_INTENTOS_FALLIDOS) {
                        bloquearAcceso();
                    }
                }
            }
        }

        //bloquea el acceso despues de un numero maximo de intentos fallidos.
        private void bloquearAcceso() {
            //muestra un mensaje de sesion bloqueada y establece el bloqueado en verdadero.
            Toast.makeText(this, "Sesión bloqueada. Espere un momento.", Toast.LENGTH_SHORT).show();
            bloqueado = true;
            // programa un temporizador para desbloquear la sesion despues del tiempo establecido.
            tiempoDesbloqueo = System.currentTimeMillis() + BLOQUEO_TIEMPO_MS;

            // Configurar un Handler para desbloquear la sesión después de un tiempo.
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                //desbloquea el acceso después de un período de bloqueo.
                public void run() {
                    desbloquearAcceso();
                }
            }, BLOQUEO_TIEMPO_MS);
        }

        private void desbloquearAcceso() {
            // Restablece las variables y muestra un mensaje de sesión desbloqueada.
            bloqueado = false;
            tiempoDesbloqueo = 0;
            intentosFallidos = 0;
            Toast.makeText(this, "Sesión desbloqueada. Puede intentar nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

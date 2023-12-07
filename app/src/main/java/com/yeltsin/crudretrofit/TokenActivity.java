package com.yeltsin.crudretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class TokenActivity extends AppCompatActivity {

    private EditText editTextCode;
    private TextView textViewResult;
    private String generatedCode;
    private int attempts = 0;
    private long blockStartTime = 0;
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_TIME_MS = 30 * 1000; // 30 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        editTextCode = findViewById(R.id.editTextCode);
        textViewResult = findViewById(R.id.textViewResult);

        Button btnGenerateCode = findViewById(R.id.btnGenerateCode);
        btnGenerateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked()) {
                    showToast("Sesión bloqueada por múltiples intentos");
                } else {
                    generatedCode = generateRandomCode();
                    showToast("Código generado: " + generatedCode);
                }
            }
        });

        Button btnValidateCode = findViewById(R.id.btnValidateCode);
        btnValidateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked()) {
                    showToast("Sesión bloqueada por múltiples intentos");
                } else {
                    String enteredCode = editTextCode.getText().toString();
                    if (enteredCode.equals(generatedCode)) {
                        startMainActivity2();
                    } else {
                        attempts++;
                        if (attempts >= MAX_ATTEMPTS) {
                            blockStartTime = SystemClock.elapsedRealtime();
                            showToast("Sesión bloqueada por múltiples intentos");
                        } else {
                            showToast("Código no válido, intento #" + attempts);
                        }
                    }
                }
            }
        });

        // Maneja el desbloqueo después de 30 segundos.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isBlocked() && SystemClock.elapsedRealtime() - blockStartTime >= BLOCK_TIME_MS) {
                    unblockSession();
                }
                handler.postDelayed(this, 1000); // Comprueba cada segundo.
            }
        }, 1000);
    }

    private String generateRandomCode() {
        Random rand = new Random();
        int code = 10000 + rand.nextInt(90000);
        return String.valueOf(code);
    }

    private void showToast(String message) {
        Toast.makeText(TokenActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isBlocked() {
        return attempts >= MAX_ATTEMPTS && (SystemClock.elapsedRealtime() - blockStartTime) < BLOCK_TIME_MS;
    }

    private void unblockSession() {
        attempts = 0;
        blockStartTime = 0;
    }

    private void startMainActivity2() {
        Intent intent = new Intent(TokenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.yeltsin.crudretrofit.activities;
// Importaciones necesarias
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.yeltsin.crudretrofit.MainActivity;
import com.yeltsin.crudretrofit.R;
import com.yeltsin.crudretrofit.dto.ProductDTO;
import com.yeltsin.crudretrofit.interfaces.CRUDInterface;
import com.yeltsin.crudretrofit.model.Product;
import com.yeltsin.crudretrofit.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Clase principal que extiende de AppCompatActivity
public class CreateActivity extends AppCompatActivity {
    EditText nameText, priceText;
    Button createButton;
    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_create);

        // Inicializa los elementos de la interfaz de usuario
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        createButton = findViewById(R.id.createButton);

        // Agrega listeners a los campos de texto para habilitar/deshabilitar el botón de creación
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Establece el estado inicial del botón de creación
        createButton.setEnabled(buttonEnabled());

        // Agrega un listener al botón de creación
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un objeto ProductDTO con los datos ingresados
                ProductDTO dto = new ProductDTO(nameText.getText().toString(), Integer.valueOf(priceText.getText().toString()));
                // Llama al método create para realizar la operación de creación
                create(dto);
            }
        });
    }

    // Método para realizar la creación del producto utilizando Retrofit
    private void create(ProductDTO dto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);

        // Realiza la llamada a la API para crear un nuevo producto
        Call<Product> call = crudInterface.create(dto);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                // Maneja la respuesta exitosa de la API
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                Product product = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + ", agregado!!", Toast.LENGTH_LONG);
                toast.show();
                // Llama al método para volver a la actividad principal
                callMain();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Maneja el fallo en la llamada a la API
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

    // Método para iniciar la actividad principal
    private void callMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // Método para determinar si el botón de creación debe estar habilitado o no
    private boolean buttonEnabled() {
        return nameText.getText().toString().trim().length() > 0 && priceText.getText().toString().trim().length() > 0;
    }
}

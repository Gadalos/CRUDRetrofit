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
public class EditActivity extends AppCompatActivity {

    Product product;
    EditText nameText, priceText;
    Button editButton;
    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_edit);

        // Obtiene el objeto Product pasado desde la actividad anterior
        Intent detailIntent = getIntent();
        product = (Product) detailIntent.getSerializableExtra("product");

        // Inicializa los elementos de la interfaz de usuario y establece los valores actuales del producto
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));

        // Inicializa y agrega listeners al botón de edición y a los campos de texto
        editButton = findViewById(R.id.editButton);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Establece el estado inicial del botón de edición
        editButton.setEnabled(buttonEnabled());

        // Agrega un listener al botón de edición
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un objeto ProductDTO con los datos modificados
                ProductDTO dto = new ProductDTO(nameText.getText().toString(), Integer.valueOf(priceText.getText().toString()));
                // Llama al método edit para realizar la operación de edición
                edit(dto);
            }
        });
    }

    // Método para realizar la edición del producto utilizando Retrofit
    private void edit(ProductDTO dto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);

        // Obtiene el ID del producto a editar
        int id = product.getId();

        // Realiza la llamada a la API para editar el producto
        Call<Product> call = crudInterface.edit(id, dto);
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
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + ", actualizado!!", Toast.LENGTH_LONG);
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

    // Método para determinar si el botón de edición debe estar habilitado o no
    private boolean buttonEnabled() {
        return nameText.getText().toString().trim().length() > 0 && priceText.getText().toString().trim().length() > 0;
    }
}

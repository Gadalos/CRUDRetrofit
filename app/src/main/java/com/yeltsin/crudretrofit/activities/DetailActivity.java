package com.yeltsin.crudretrofit.activities;
// Importaciones necesarias
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yeltsin.crudretrofit.MainActivity;
import com.yeltsin.crudretrofit.R;
import com.yeltsin.crudretrofit.fragments.DeleteFragment;
import com.yeltsin.crudretrofit.interfaces.CRUDInterface;
import com.yeltsin.crudretrofit.interfaces.DeleteInterface;
import com.yeltsin.crudretrofit.model.Product;
import com.yeltsin.crudretrofit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Clase principal que extiende de AppCompatActivity e implementa DeleteInterface
public class DetailActivity extends AppCompatActivity implements DeleteInterface {

    TextView idText, nameText, priceText;
    Button editButton, deleteButton;
    Product product;
    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_detail);

        // Inicializa los elementos de la interfaz de usuario
        idText = findViewById(R.id.idText);
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        int id = getIntent().getExtras().getInt("id");

        // Inicializa y agrega listeners a los botones de edición y eliminación
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEdit();
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(product.getId());
            }
        });

        // Obtiene los detalles del producto mediante su ID
        getOne(id);
    }

    // Método para obtener los detalles de un producto por su ID
    private void getOne(int id) {
        crudInterface = getCrudInterface();
        Call<Product> call = crudInterface.getOne(id);
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
                product = response.body();
                // Actualiza la interfaz con los detalles del producto
                idText.setText(String.valueOf(product.getId()));
                nameText.setText(product.getName());
                priceText.setText(String.valueOf(product.getPrice()));
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

    // Método para iniciar la actividad de edición
    private void callEdit() {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    // Método de la interfaz DeleteInterface para mostrar el diálogo de eliminación
    @Override
    public void showDeleteDialog(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteFragment deleteFragment = new DeleteFragment("Eliminar producto ", product.getId(), this);
        deleteFragment.show(fragmentManager, "Alert");
    }

    // Método de la interfaz DeleteInterface para realizar la eliminación del producto
    @Override
    public void delete(int id) {
        crudInterface = getCrudInterface();
        Call<Product> call = crudInterface.delete(id);
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
                product = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + ", eliminado!!", Toast.LENGTH_LONG);
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

    // Método para obtener una instancia de CRUDInterface utilizando Retrofit
    private CRUDInterface getCrudInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        return crudInterface;
    }

    // Método para iniciar la actividad principal
    private void callMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

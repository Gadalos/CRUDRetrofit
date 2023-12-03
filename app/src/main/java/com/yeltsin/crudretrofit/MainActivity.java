package com.yeltsin.crudretrofit;
// Importaciones necesarias
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yeltsin.crudretrofit.activities.CreateActivity;
import com.yeltsin.crudretrofit.adapters.ProductsAdapter;
import com.yeltsin.crudretrofit.interfaces.CRUDInterface;
import com.yeltsin.crudretrofit.model.Product;
import com.yeltsin.crudretrofit.utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Clase MainActivity que extiende de AppCompatActivity
public class MainActivity extends AppCompatActivity {

    // Atributos de la clase
    List<Product> products;
    CRUDInterface crudInterface;
    ListView listView;
    FloatingActionButton createButton;

    // Método onCreate que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de las vistas
        listView = findViewById(R.id.listView);
        createButton = findViewById(R.id.createButton);

        // Configuración del OnClickListener para el botón de creación
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreate();
            }
        });

        // Llamada al método para obtener todos los productos
        getAll();
    }

    // Método para obtener todos los productos mediante Retrofit
    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Product>> call = crudInterface.getAll();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    // Manejo de errores en caso de respuesta no exitosa
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                // Obtención de la lista de productos y configuración del adaptador para el ListView
                products = response.body();
                ProductsAdapter productsAdapter = new ProductsAdapter(products, getApplicationContext());
                listView.setAdapter(productsAdapter);

                // Impresión de la información de los productos en el Log
                products.forEach(p -> Log.i("Prods: ", p.toString()));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Manejo de errores en caso de fallo en la llamada
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

    // Método para iniciar la actividad de creación de productos
    private void callCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
        startActivity(intent);
    }
}

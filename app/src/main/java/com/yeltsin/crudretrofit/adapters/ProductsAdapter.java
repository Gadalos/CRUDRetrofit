package com.yeltsin.crudretrofit.adapters;
// Importaciones necesarias
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yeltsin.crudretrofit.R;
import com.yeltsin.crudretrofit.activities.DetailActivity;
import com.yeltsin.crudretrofit.model.Product;

import java.util.List;

// Clase ProductsAdapter que extiende de BaseAdapter
public class ProductsAdapter extends BaseAdapter {

    // Lista de productos y contexto de la aplicación
    List<Product> products;
    Context context;

    // Elementos de la interfaz de usuario
    TextView nameText;
    Button viewButton;

    // Constructor del adaptador
    public ProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    // Devuelve la cantidad de elementos en la lista
    @Override
    public int getCount() {
        return products.size();
    }

    // Devuelve el objeto en una posición específica
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    // Devuelve el ID asociado con un elemento en una posición específica
    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    // Método para crear y retornar la vista de cada elemento en la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Si la vista de elemento no existe, infla la vista desde el archivo de diseño XML
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list, parent, false);
        }

        // Obtiene la referencia a los elementos de la interfaz de usuario
        nameText = convertView.findViewById(R.id.nameText);
        viewButton = convertView.findViewById(R.id.viewButton);

        // Establece el nombre del producto en el TextView
        nameText.setText(products.get(position).getName());

        // Agrega un listener al botón de vista para iniciar la actividad de detalles
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetail(products.get(position).getId());
            }
        });

        return convertView;
    }

    // Método para iniciar la actividad de detalles
    private void callDetail(int id){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

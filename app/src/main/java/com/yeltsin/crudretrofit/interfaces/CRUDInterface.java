package com.yeltsin.crudretrofit.interfaces;
// Importaciones necesarias
import com.yeltsin.crudretrofit.dto.ProductDTO;
import com.yeltsin.crudretrofit.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// Interfaz CRUDInterface para definir las operaciones CRUD en productos
public interface CRUDInterface {

    // Método para obtener todos los productos
    @GET("product")
    Call<List<Product>> getAll();

    // Método para obtener un producto por su ID
    @GET("product/{id}")
    Call<Product> getOne(@Path("id") int id);

    // Método para crear un nuevo producto
    @POST("product")
    Call<Product> create(@Body ProductDTO dto);

    // Método para editar un producto existente por su ID
    @PUT("product/{id}")
    Call<Product> edit(@Path("id") int id, @Body ProductDTO dto);

    // Método para eliminar un producto por su ID
    @DELETE("product/{id}")
    Call<Product> delete(@Path("id") int id);
}

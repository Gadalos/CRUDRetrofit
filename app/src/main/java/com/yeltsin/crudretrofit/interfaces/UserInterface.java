package com.yeltsin.crudretrofit.interfaces;

import com.yeltsin.crudretrofit.dto.UserDTO;
import com.yeltsin.crudretrofit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserInterface {

    // Método para obtener todos los productos
    @GET("user")
    Call<List<User>> getAll();

    // Método para obtener un producto por su ID
    @GET("user/{id}")
    Call<User> getOne(@Path("id") int id);

    // Método para crear un nuevo producto
    @POST("user")
    Call<User> create(@Body UserDTO dto);

    // Método para editar un producto existente por su ID
    @PUT("user/{id}")
    Call<User> edit(@Path("id") int id, @Body UserDTO dto);

    // Método para eliminar un producto por su ID
    @DELETE("user/{id}")
    Call<User> delete(@Path("id") int id);
}

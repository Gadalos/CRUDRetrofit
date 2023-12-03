package com.yeltsin.crudretrofit.model;

import java.io.Serializable;

// Clase Product que implementa la interfaz Serializable
public class Product implements Serializable {

    // Atributos de la clase
    private int id;
    private String name;
    private int price;

    // Constructor vacío por defecto
    public Product() {
    }

    // Constructor con parámetros para establecer valores iniciales
    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Método toString para proporcionar una representación de cadena de la instancia de Product
    @Override
    public String toString() {
        return "Id: " + getId() + ", Name: " + getName() + ", Price: " + getPrice();
    }
}

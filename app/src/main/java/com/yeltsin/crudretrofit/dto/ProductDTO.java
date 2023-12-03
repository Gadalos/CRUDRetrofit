package com.yeltsin.crudretrofit.dto;
// Clase ProductDTO que representa un objeto de transferencia de datos para productos
public class ProductDTO {

    // Atributos del DTO
    private String name;
    private int price;

    // Constructor vacío por defecto
    public ProductDTO() {
    }

    // Constructor con parámetros para establecer valores iniciales
    public ProductDTO(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Métodos getter y setter para acceder y modificar los atributos

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
}

package com.yeltsin.crudretrofit.dto;

public class UserDTO {

    // Atributos del DTO
    private String nombre;
    private String correo;
    private String clave;

    // Constructor vacío por defecto
    public UserDTO() {
    }

    // Constructor con parámetros para establecer valores iniciales

    public UserDTO(String nombre, String correo, String clave) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }


    // Métodos getter y setter para acceder y modificar los atributos


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}

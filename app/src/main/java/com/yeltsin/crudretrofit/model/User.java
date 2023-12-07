package com.yeltsin.crudretrofit.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String nombre;
    private String correo;
    private String clave;

    public User() {
    }

    public User(int id, String nombre, String correo, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Id: " + getId() + ", correo: " + getCorreo();
    }
}

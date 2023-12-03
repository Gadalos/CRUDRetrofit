package com.yeltsin.crudretrofit.interfaces;

// Interfaz DeleteInterface para la comunicación entre fragmentos y actividades
public interface DeleteInterface {

    // Método para mostrar un diálogo de eliminación
    void showDeleteDialog(int id);

    // Método para realizar la eliminación
    void delete(int id);
}


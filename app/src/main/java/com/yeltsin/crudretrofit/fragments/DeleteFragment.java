package com.yeltsin.crudretrofit.fragments;
// Importaciones necesarias
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import com.yeltsin.crudretrofit.interfaces.DeleteInterface;

// Clase DeleteFragment que extiende de DialogFragment
public class DeleteFragment extends DialogFragment {

    // Atributos del fragmento
    private String message;
    private int id;
    private DeleteInterface deleteInterface;

    // Constructor que recibe el mensaje, el ID y la interfaz de eliminación
    public DeleteFragment(String message, int id, DeleteInterface deleteInterface) {
        this.message = message;
        this.id = id;
        this.deleteInterface = deleteInterface;
    }

    // Método onCreateDialog para crear y devolver el diálogo
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Crea un constructor de AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configura el mensaje y los botones del diálogo
        builder.setMessage(message + id + "?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Llama al método de eliminación en la interfaz
                        deleteInterface.delete(id);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Registra un mensaje en el Log si se hace clic en Cancelar
                        Log.i("Action: ", "cancel");
                    }
                });

        // Devuelve el diálogo creado
        return builder.create();
    }
}

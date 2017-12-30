package com.mpardalm.mi_agenda.modelo;

import android.content.ContentValues;

import com.mpardalm.mi_agenda.dataBase.AgendaC;

/**
 * Created by mpardalm on 6/12/17.
 */

public class Contacto {

    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono){
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public ContentValues generaValores(){

        ContentValues valores = new ContentValues();

        valores.put(AgendaC.AgendaE.CN_NAME, nombre);
        valores.put(AgendaC.AgendaE.CN_PHONE, telefono);

        return valores;
    }

    //Getter && Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

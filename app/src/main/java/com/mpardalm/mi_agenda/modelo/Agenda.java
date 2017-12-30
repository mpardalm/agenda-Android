package com.mpardalm.mi_agenda.modelo;

import android.content.Context;

import com.mpardalm.mi_agenda.dataBase.AgendaDBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpardalm on 6/12/17.
 */

public class Agenda {
    private static Agenda agenda;
    public List<Contacto> listaContactos;
    public List<String> nombres;
    private AgendaDBManager manager;

    private Agenda(Context context){

        manager = new AgendaDBManager(context);
        listaContactos = manager.findAll();
        nombres = new ArrayList<>();

        for(Contacto contacto:listaContactos){
            nombres.add(contacto.getNombre()+"<"+contacto.getTelefono()+">");
        }
    }

    public Agenda crearAgenda(Context context){

        if (agenda ==null)
            agenda = new Agenda(context);
        return agenda;
    }

    public List<Contacto> getListaContactos() {
        return listaContactos;
    }

    public void setListaContactos(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public List<String> getNombres() {
        return nombres;
    }

    public void setNombres(List<String> nombres) {

        this.nombres = nombres;
    }
}

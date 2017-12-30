package com.mpardalm.mi_agenda.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpardalm.mi_agenda.modelo.Contacto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpardalm on 8/12/17.
 */

public class AgendaDBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "Agenda.db";
    public static final int DB_SCHEME_VERSION = 1;

    public AgendaDBManager(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + AgendaC.AgendaE.TABLE_NAME+ " ("
                + AgendaC.AgendaE._ID+ " integer primary key autoincrement, "
                + AgendaC.AgendaE.CN_NAME+ " text not null, "
                + AgendaC.AgendaE.CN_PHONE+ " text not null);");

        Contacto contacto = new Contacto("Miguel", "617717676");

        sqLiteDatabase.insert(AgendaC.AgendaE.TABLE_NAME, null, contacto.generaValores());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Contacto> findAll(){
        List<Contacto> contactos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + AgendaC.AgendaE.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Contacto contacto = new Contacto (cursor.getColumnName(1), cursor.getColumnName(2));
                contactos.add(contacto);
            } while(cursor.moveToNext());
        }
        return contactos;
    }

    public Cursor cargarCursorContactos(){

        return getReadableDatabase().query(AgendaC.AgendaE.TABLE_NAME, null,null, null, null, null, null);
    }

    public long insetarContacto(Contacto contacto){
        SQLiteDatabase bd = getWritableDatabase();
        long control = 0;
        if(contacto.getNombre().equals("") || contacto.getTelefono().equals("")){
            control = 2;
        }else if(existeContacto(contacto)){
            modificarContacto(contacto);
            control = 1;
        }else{
                bd.insert(AgendaC.AgendaE.TABLE_NAME, null, contacto.generaValores());
        }
        return control;
    }

    private boolean existeContacto(Contacto contacto){
        SQLiteDatabase bdAux = getReadableDatabase();

        Cursor res = bdAux.query(AgendaC.AgendaE.TABLE_NAME, null, AgendaC.AgendaE.CN_NAME+" = ? ", new String[]{contacto.getNombre()}, null, null, null);

        return (res.getCount() == 1) ? true : false;
    }

    public void modificarContacto(Contacto contacto){
        SQLiteDatabase bd = getWritableDatabase();

        bd.update(AgendaC.AgendaE.TABLE_NAME, contacto.generaValores(), AgendaC.AgendaE.CN_NAME+ " = ? ", new String[]{contacto.getNombre()});
    }

    public void eliminarContacto(Contacto contacto){
        SQLiteDatabase bd = getWritableDatabase();
        bd.delete(AgendaC.AgendaE.TABLE_NAME, AgendaC.AgendaE.CN_NAME+ " = ? ", new String[]{contacto.getNombre()});
    }

    public Cursor buscarContactoNombre(String nombre) {
        SQLiteDatabase bdAux = getReadableDatabase();

        return bdAux.query(AgendaC.AgendaE.TABLE_NAME, null, AgendaC.AgendaE.CN_NAME + " like ?", new String[]{nombre+"%"}, null, null, null);
    }
}

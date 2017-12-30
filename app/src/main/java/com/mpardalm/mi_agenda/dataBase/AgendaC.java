package com.mpardalm.mi_agenda.dataBase;

import android.provider.BaseColumns;

/**
 * Created by mpardalm on 8/12/17.
 */

public class AgendaC {

    public static abstract class AgendaE implements BaseColumns{
        public static final String TABLE_NAME = "contacto";

        public static final String CN_NAME = "nombre";
        public static final String CN_PHONE = "telefono";
    }
}

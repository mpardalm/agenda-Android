package com.mpardalm.mi_agenda;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mpardalm.mi_agenda.dataBase.AgendaC;
import com.mpardalm.mi_agenda.dataBase.AgendaDBManager;
import com.mpardalm.mi_agenda.modelo.Agenda;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AgendaDBManager manager;
    private Cursor cursor;
    private ListView listaContactos;
    private SimpleCursorAdapter adapter;
    private TextView textViewSearch;
    private ImageButton buttonSearch;
    private ImageButton buttonAdd;
    Cursor value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new AgendaDBManager(this);

        listaContactos = (ListView) findViewById(R.id.listView);
        textViewSearch = (TextView) findViewById(R.id.editTextSearch);
        buttonSearch = (ImageButton) findViewById(R.id.findButton);
        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);

        buttonSearch.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        listaContactos.setClickable(true);
        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value = (Cursor) listaContactos.getItemAtPosition(position);
                verContacto();
            }
        });

        generarContactos();
    }

    public void generarContactos(){
        cursor = manager.cargarCursorContactos();

        //Layout predefinido de android. En text1 se muestran los datos de la columna NAME, en text2 los datos de la columna PHONE
        String[] from = new String[]{AgendaC.AgendaE.CN_NAME, AgendaC.AgendaE.CN_PHONE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listaContactos.setAdapter(adapter);
    }

    public void addContacto(){
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("nombre", textViewSearch.getText().toString());
        startActivity(intent);
    }

    public void verContacto(){
        Intent intent = new Intent(this, InfoContactActivity.class);
        intent.putExtra("name", value.getString(1));
        intent.putExtra("phone", value.getString(2));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.findButton){
            buscarContacto();
        }else if(view.getId() == R.id.buttonAdd){
            addContacto();
        }
    }

    private void buscarContacto(){
        if (!(textViewSearch.getText().toString().equals(""))){
            cursor = manager.buscarContactoNombre(textViewSearch.getText().toString());
            if(cursor.getCount() == 0){
                Toast.makeText(this, R.string.encontrado, Toast.LENGTH_SHORT).show();
                cursor = manager.cargarCursorContactos();
            }
        }else if((textViewSearch.getText().toString().equals(""))){
            cursor = manager.cargarCursorContactos();
        }
        adapter.changeCursor(cursor);
    }
}

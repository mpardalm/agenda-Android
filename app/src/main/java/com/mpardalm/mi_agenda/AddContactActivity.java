package com.mpardalm.mi_agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mpardalm.mi_agenda.dataBase.AgendaDBManager;
import com.mpardalm.mi_agenda.modelo.Contacto;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText nombreUsuario, telefonoUsuario;
    public Button volver, guardar;
    public AgendaDBManager manager;
    public String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nombreUsuario = (EditText) findViewById(R.id.nombreContacto);
        telefonoUsuario = (EditText) findViewById(R.id.telefonoUsuario);
        volver = (Button) findViewById(R.id.volverButton);
        guardar = (Button) findViewById(R.id.saveContactButton);

        Bundle bundle = getIntent().getExtras();
        nombre = bundle.getString("nombre");
        nombreUsuario.setText(nombre);

        volver.setOnClickListener((View.OnClickListener) this);
        guardar.setOnClickListener((View.OnClickListener) this);

    }

    private void volver (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.volverButton){
            volver();
        }else if(view.getId() == R.id.saveContactButton){
            guardarContacto();
        }
    }

    private void guardarContacto() {
        String nombre = nombreUsuario.getText().toString().trim();
        String telefono = telefonoUsuario.getText().toString().trim();
        Contacto c = new Contacto(nombre, telefono);

        manager = new AgendaDBManager(this);

        long res = manager.insetarContacto(c);
        if(res == 0){
            Toast.makeText(this, R.string.creado, Toast.LENGTH_SHORT).show();
            volver();
        }else if(res == 2){
            createSimpleDialog();
        }else if(res == 1){
            Toast.makeText(this, R.string.actualizado, Toast.LENGTH_SHORT).show();
            volver();
        }
    }

    private void createSimpleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle(R.string.errorTitulo);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(R.string.mensaje);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // acciones a realizar en caso positivo
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // acciones a realizar en caso negativo
                dialog.cancel();
            }
        });
        builder.show();
    }
}

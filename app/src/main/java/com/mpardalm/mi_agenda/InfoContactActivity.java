package com.mpardalm.mi_agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoContactActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre, nombreContacto, telefono, telefonoConacto;
    private Button llamar, volver;
    private String phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contact);

        nombre = (TextView) findViewById(R.id.nombreText);
        nombreContacto = (TextView) findViewById(R.id.nombreMostrar);
        telefono = (TextView) findViewById(R.id.telefonoText);
        telefonoConacto = (TextView) findViewById(R.id.telefonoMostrar);
        llamar = (Button) findViewById(R.id.llamarButton);
        volver = (Button) findViewById(R.id.volverButton);

        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("name");
        phone = bundle.getString("phone");

        nombreContacto.setText(name);
        telefonoConacto.setText(phone);

        llamar.setOnClickListener(this);
        volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.volverButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.llamarButton) {
            Toast.makeText(getApplicationContext(), R.string.llamada, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }
}

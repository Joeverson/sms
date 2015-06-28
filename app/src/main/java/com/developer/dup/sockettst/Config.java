package com.developer.dup.sockettst;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import tools.Comunication;
import tools.DB;
import tools.Message;

import static android.widget.AdapterView.OnItemSelectedListener;


public class Config extends ActionBarActivity {

    EditText ip;
    EditText name;
    DB db;
    Cursor users;
    Comunication socket;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        db = new DB(this);

        ip = (EditText) findViewById(R.id.inputIp);
        name = (EditText) findViewById(R.id.inputName);

        if((users = db.selectInfo()) != null){
            name.setText(users.getString(0));
            ip.setText(users.getString(1));
        }

        Button btnSair = (Button) findViewById(R.id.btnGoBack);
        btnSair.setOnClickListener(btnGoBack);

        Button btnSave = (Button) findViewById(R.id.btnSaveConfig);
        btnSave.setOnClickListener(btnSaveConfigs);
    }

    View.OnClickListener btnGoBack = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            onBackPressed();
        }
    };


    View.OnClickListener btnSaveConfigs = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            if (db.setData(name.getText().toString(), ip.getText().toString())){
                
                // envia para o servidor algums informações sobre o dispositivo para deixalo ativo para conversa
                socket = new Comunication(ip.getText().toString(), 2808, getApplicationContext());

                // envia o serviço para o servidor infromando que ele ta online
                socket.sendService("saveMe", name.getText().toString());

                Toast.makeText(getBaseContext(), "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }else
                Toast.makeText(getBaseContext(), "Ocorreu um erro ao gravar!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*public void fillSpinner() throws IOException, ClassNotFoundException {
         //chamando socket
        socket = new Comunication(usersss.getString(1), 2808, getApplicationContext());
        Message obj = new Message("spinner", userss.getString(0));

        socket.conn();
        socket.send(obj);
        Message serviceResponse = socket.receved();
        socket.closeAll();

        ArrayAdapter aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) serviceResponse.getData());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner = (Spinner) findViewById(R.id.userssOn);
        spinner.setAdapter(aa);

    } */
}

package com.developer.dup.sockettst;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import tools.Comunication;
import tools.DB;
import tools.Message;
import tools.ServerService;

public class MainActivity extends ActionBarActivity {

    EditText inputText;
    TextView outText;
    Comunication socket;
    DB db;
    Cursor user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);// isntanciando db
        user = db.selectInfo();// pega os dados do banco

        //new ServerService(this, (EditText) findViewById(R.id.editText)); // inicia o serviço servidor

        if( user  == null){
            Intent intent = new Intent(getApplicationContext(), Config.class);
            startActivity(intent);
        }else{
            // envia para o servidor algums informações sobre o dispositivo para deixalo ativo para conversa
            socket = new Comunication(user.getString(1), 2808, this);

            // envia o serviço para o servidor infromando que ele ta online
            socket.sendService("saveMe", user.getString(0));
        }

        inputText = (EditText) findViewById(R.id.editText); // pega o objeto de entrada de texto
        outText = (TextView) findViewById(R.id.outText);


        // btn's sendo setados
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(talkPc);// avisa que tem um listem para o btn

        Button btnViewConfigActivity = (Button) findViewById(R.id.btnConfig);
        btnViewConfigActivity.setOnClickListener(btnViewConfig);// avisa que tem um listem para o btn

    }

    // events of click
    View.OnClickListener talkPc = new View.OnClickListener(){
        public void onClick(View arg0){

            try {
                user = db.selectInfo();
                Message obj = new Message("192.168.0.17", inputText.getText().toString(), null);


                // chamando socket
                socket = new Comunication(user.getString(1), 2808, getApplicationContext());

                socket.conn();
                socket.send(obj);
                outText.append("\n" + user.getString(0) + " - " + obj.getSms());
                inputText.setText("");


                socket.closeAll();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error ao receber do servidor", Toast.LENGTH_SHORT).show();
            }
        }
    };


    View.OnClickListener btnViewConfig = new View.OnClickListener(){
        public void onClick(View arg0){
            Intent intent = new Intent(getApplicationContext(), Config.class);
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Voltouuu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // envia para o servidor algums informações sobre o dispositivo para deixalo ativo para conversa
        socket = new Comunication(user.getString(1), 2808, this);

        // envia o serviço para o servidor infromando que ele ta online
        socket.sendService("bye", user.getString(0));
    }
}

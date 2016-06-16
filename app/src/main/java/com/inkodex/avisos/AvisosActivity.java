package com.inkodex.avisos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AvisosActivity extends AppCompatActivity {

    private ListView mListView; //creamos una clase privada mListview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);
        mListView = (ListView)findViewById(R.id.listView);// busca la LisView con el id
        //the arrayAdapter is the controller in our
        //model-view-controller relationship. (controller)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( // arrayAdapater toma 3 parametros
                //context
                this, // el primer parametro es el contexto
                R.layout.avisos_row,// adapater tiene que saber cual es el layout
                //row (view)
                R.id.row_text, //el campo o los campos se le pasa el id
                //data (model) con datos falsos para probar nuestra Listview
                new String[]{"first record","second record","third record"});// el ultimo parametro es un string
        mListView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avisos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_nuevo:
                //crear un nuevo aviso
                Log.d(getLocalClassName(),"crear nuevo aviso");
                return true;
            case R.id.action_salir:
                finish();// aca se cierra la aplicación
                return true;
            default:
                return false;
        }
    }
}

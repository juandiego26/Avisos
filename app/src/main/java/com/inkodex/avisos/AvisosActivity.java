package com.inkodex.avisos;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class AvisosActivity extends AppCompatActivity {

    private ListView mListView; //creamos una clase privada mListview
    private AvisosDBAdapter mDBAdapter;
    private AvisosSimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);
        mListView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.listView);
        mListView.setDivider(null);
        mDBAdapter = new AvisosDBAdapter(this);
        mDBAdapter.open();

       if (savedInstanceState == null){

            //limpiar todos los datos
            mDBAdapter.deleteAllReminders();
            //add algunos datos
            mDBAdapter.CreateReminder("Visitar el centro de recogida", true);
            mDBAdapter.CreateReminder("Enviar los regalos prometidos",false);
            mDBAdapter.CreateReminder("Hacer la compra semanal", false);
            mDBAdapter.CreateReminder("Comprobar el correo", false);
        }

        Cursor cursor = mDBAdapter.fetchAllReminders();
        // desde las columnas definidas en la base de datos
        String[] from = new String[]{
                AvisosDBAdapter.COL_CONTENT
        };

        // a la id de views en el layout

        int[] to = new int[]{
                R.id.row_text
        };

        mCursorAdapter = new AvisosSimpleCursorAdapter(
                //context
                AvisosActivity.this,
                // el layout de la fila
                R.layout.avisos_row,
                //cursor
                cursor,
                // desde columnas definidas en la base de datos
                from,
                // a las ids de los views en el Layout
                to,
                //flag no usado
                0);
        // el cursorAdapter (controller) está ahora actualizando la Listview (Vista)
        // con los datos de la base de datos (Modelo)

        mListView.setAdapter(mCursorAdapter);
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

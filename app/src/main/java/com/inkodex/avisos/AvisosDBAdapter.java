package com.inkodex.avisos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JuanDiego on 16/06/2016.
 */
public class AvisosDBAdapter {
    //Este bloque de codigo define los nombres de las columnas
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";

    // Estos son los indices correspondientes
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;

    //Usado for Logging

    private static final String TAG = "AvisosDbAdapter";

    private DatabaseHelper mDbHelper; // objetos API Database
    private SQLiteDatabase mDb; // objeto API Database

    private static final String DATABASE_NAME = "bda_remdrs"; //constantes para el nombre de la BD
    private static final String TABLE_NAME = "tbl_remdrs"; // nombre de la tabla pincipal
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx; // objeto context

    //declaración SQL para crar la base de datos

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, "+
                    COL_CONTENT + " TEXT, "+
                    COL_IMPORTANT + " INTEGER );";
    public AvisosDBAdapter (Context ctx){
        this.mCtx = ctx;
    }
    //abrir
    public void open()throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    //cerrar
    public void close(){
        if (mDbHelper != null){
            mDbHelper.close();
        }
    }

    //CREATE
    // Ten en cuenta que el Id será creado automáticamente
    public void CreateReminder (String name, boolean important){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, name);
        values.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null, values);
    }

    //sobrecargado para tomar un aviso
    public long CreateReminder (Aviso reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, reminder.getContent());//nombre contacto
        values.put(COL_IMPORTANT, reminder.getImportant()); // numero de telefono de contacto
        //insertar fila
        return mDb.insert(TABLE_NAME, null, values);
    }

    //READ
    public Aviso fetchReminderById (int id){
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT},COL_ID
        + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return new Aviso(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_CONTENT),
                cursor.getInt(INDEX_IMPORTANT)
        );
    }

    public Cursor fetchAllReminders(){
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT},
                null, null, null, null, null);

        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //UPDATE
    public void updateReminder (Aviso reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, reminder.getContent());
        values.put(COL_IMPORTANT, reminder.getImportant());
        mDb.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(reminder.getId())} );
    }

    //DELETE
    public void deleteReminderById (int nId){
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }

    public void deleteAllReminders(){
        mDb.delete(TABLE_NAME, null, null);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper { //database Helper extiende a la clase SQLiteOpenHelper
        DatabaseHelper (Context context){ //constructor donde se inicializa la BD
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) { // este metodo es llamado para cuando la aplicacion se ejecuta por primera vez
            Log.w(TAG, DATABASE_CREATE); // y la DB no ha sido creada
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // este metodo es llamado cuando la BD necesita ser actualizada
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}


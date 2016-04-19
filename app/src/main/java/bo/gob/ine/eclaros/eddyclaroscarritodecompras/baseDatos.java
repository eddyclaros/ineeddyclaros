package bo.gob.ine.eclaros.eddyclaroscarritodecompras;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eclaros on 19/04/2016.
 */
public class baseDatos extends SQLiteOpenHelper
{
    public static final String NOMBREDB= "compras.sqlite";
    public static final int VERSION=1;

    public baseDatos(Context context, int VERSION){
        super(context,NOMBREDB,null,VERSION);

    }

    public void onCreate(SQLiteDatabase db)
    {
        //db.execSQL("create table usuario (id integer primary key autoimcrement not null,usuario varchar,contraseña varchar);");
        db.execSQL("create table cliente (id integer primary key autoincrement not null, nombres varchar, apellidos varchar, ci varchar,usuario varchar);");
        db.execSQL("create table producto (id integer primary key autoincrement not null, producto text, precio integer);");
        db.execSQL("create table pedido (id integer primary key autoincrement not null, id_producto integer, id_cliente integer,cant_pedido integer,fecha_pedido date);");

        Log.d("Todas las tablas: ","se crearon las tablas");
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }


}

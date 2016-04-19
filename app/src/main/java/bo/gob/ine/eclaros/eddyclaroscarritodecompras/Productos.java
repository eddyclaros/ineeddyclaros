package bo.gob.ine.eclaros.eddyclaroscarritodecompras;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eclaros on 19/04/2016.
 */
public class Productos extends AppCompatActivity {
    private EditText producto;
    private  EditText precio;
    private Button btnGuardar;
    private TableLayout tabla;
    private TableRow fila;
    TableRow.LayoutParams layoutFila;

    private SQLiteDatabase db;
    public static final int VERSION=1;

    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        context = this;
        baseDatos crearBD = new baseDatos(context, VERSION);
        db = crearBD.getWritableDatabase();

        producto = (EditText) findViewById(R.id.txtNomProducto);
        precio = (EditText) findViewById(R.id.txtPrecio);
        btnGuardar = (Button) findViewById(R.id.btnRegProd);
        tabla = (TableLayout) findViewById(R.id.tabla);
        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("producto", producto.getText().toString().trim());
                values.put("precio", precio.getText().toString().trim());
                db.insert("producto", null, values);
                db.close();
                Toast.makeText(getApplicationContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();
                reiniciarActividad();

            }
        });

        agregarFilas("producto", "precio", "0");
        Cursor productos_existentes = db.rawQuery("SELECT id,producto,precio FROM producto", null);

        if (productos_existentes.moveToFirst()) {
            do {
                agregarFilas(productos_existentes.getString(1), productos_existentes.getString(2), productos_existentes.getString(0));
            } while (productos_existentes.moveToNext());
        }
    }

    private void  reiniciarActividad(){
        Intent a = new Intent(getApplicationContext(),Productos.class);
        finish();
        startActivity(a);
    }
    private  void agregarFilas(String prod, String precio1, String id) {
        fila = new TableRow(this);
        fila.setLayoutParams(layoutFila);

        TextView nombre_producto = new TextView(this);
        TextView precio_producto = new TextView(this);

        nombre_producto.setText(prod);
        nombre_producto.setBackgroundResource(R.drawable.celda_cuerpo);

        precio_producto.setText(precio1);
        precio_producto.setBackgroundResource(R.drawable.celda_cuerpo);
        precio_producto.setGravity(Gravity.RIGHT);

        nombre_producto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 5));

        precio_producto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 5));

        fila.addView(nombre_producto);
        fila.addView(precio_producto);

        if (id.compareTo("0") != 0) {

            ImageView editar = new ImageView(this);
            ImageView eliminar = new ImageView(this);

            editar.setId(Integer.parseInt(id));
            editar.setAdjustViewBounds(true);
            editar.setBackgroundResource(R.drawable.celda_cuerpo);
            editar.setImageResource(R.drawable.refresh);

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Actualizando" + view.getId(), Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("precio", "12.00");
                    String[] args = new String[]{"" + view.getId()};
                    //db.update("Productos", values, "id LIKE ?", args);
                    db.update("producto", values, "id LIKE " + view.getId(), null);
                    reiniciarActividad();
                }
            });

            eliminar.setId(Integer.parseInt(id));
            eliminar.setAdjustViewBounds(true);
            eliminar.setBackgroundResource(R.drawable.celda_cuerpo);
            eliminar.setImageResource(R.drawable.close);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Producto Eliminado : " + view.getId(), Toast.LENGTH_SHORT).show();
                    String[] args = new String[]{"" + view.getId()};
                    db.delete("producto", "id LIKE ?", args);
                    reiniciarActividad();
                }
            });

            editar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            eliminar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

            fila.addView(editar);
            fila.addView(eliminar);

        } else {
            TextView vacio = new TextView(this);
            vacio.setText("Accion");
            vacio.setBackgroundResource(R.drawable.celda_cuerpo);
            vacio.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
            fila.addView(vacio);
        }
        tabla.addView(fila);
    }
}

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
public class Clientes extends AppCompatActivity
{
    private EditText nombre;
    private  EditText apellido;
    private EditText ci;
    private Button btnGuardar;
    private TableLayout tabla;
    private TableRow fila;
    TableRow.LayoutParams layoutFila;

    // gestion con la base de datos
    private SQLiteDatabase db;
    public static final  int VERSION=1;

    public Context context;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        context = this;
        // establecemos la conexion a la base de datos
        baseDatos crearBD= new baseDatos(context, VERSION);
        db = crearBD.getWritableDatabase();

        nombre = (EditText)findViewById(R.id.txtNombre);
        apellido = (EditText)findViewById(R.id.txtApellido);
        ci=(EditText)findViewById(R.id.txtCi);

        btnGuardar = (Button)findViewById(R.id.btnRegCliente);

        tabla = (TableLayout)findViewById(R.id.tabla);

        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ContentValues values= new ContentValues();
                values.put("nombres", nombre.getText().toString().trim());
                values.put("apellidos", apellido.getText().toString().trim());
                values.put("ci", ci.getText().toString().trim());
                db.insert("cliente", null, values);
                db.close();

                Toast.makeText(getApplicationContext(), "Cliente Registrado", Toast.LENGTH_SHORT).show();
                reiniciarActividad();
            }
        });

        agregarFilas("nombres", "apellidos", "ci","0");

        Cursor clientes_existentes = db.rawQuery("SELECT id,nombres,apellidos,ci FROM cliente", null);

        if(clientes_existentes.moveToFirst()){
            do{
                agregarFilas(clientes_existentes.getString(1), clientes_existentes.getString(2), clientes_existentes.getString(3),clientes_existentes.getString(0));
            }while (clientes_existentes.moveToNext());
        }
    }
    private void  reiniciarActividad(){
        Intent a = new Intent(getApplicationContext(), Clientes.class);
        finish();
        startActivity(a);
    }
    private  void agregarFilas(String nom, String app, String ci,String id){
        fila =new TableRow(this);
        fila.setLayoutParams(layoutFila);

        TextView nombre_cliente =new TextView(this);
        TextView apellido_cliente = new TextView(this);
        TextView ci_cliente=new TextView(this);

        nombre_cliente.setText(nom);
        nombre_cliente.setBackgroundResource(R.drawable.celda_cuerpo);

        apellido_cliente.setText(app);
        apellido_cliente.setBackgroundResource(R.drawable.celda_cuerpo);
        //precio_producto.setGravity(Gravity.RIGHT);

        ci_cliente.setText(ci);
        ci_cliente.setBackgroundResource(R.drawable.celda_cuerpo);


        nombre_cliente.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4));

        apellido_cliente.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4));

        ci_cliente.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.MATCH_PARENT,4));

        fila.addView(nombre_cliente);
        fila.addView(apellido_cliente);
        fila.addView(ci_cliente);

        if(id.compareTo("0") != 0){

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
                    values.put("apellidos", "12.00");
                    String[] args = new String[]{"" + view.getId()};
                    //db.update("Productos", values, "id LIKE ?", args);
                    db.update("cliente", values, "id LIKE " + view.getId(), null);
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
                    Toast.makeText(context, "Cliente Eliminado : " + view.getId(), Toast.LENGTH_SHORT).show();
                    String[] args = new String[]{"" + view.getId()};
                    db.delete("cliente", "id LIKE ?", args);
                    reiniciarActividad();
                }
            });

            editar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            eliminar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));

            fila.addView(editar);
            fila.addView(eliminar);

        }else{
            TextView vacio=new TextView(this);
            vacio.setText("Accion");
            vacio.setBackgroundResource(R.drawable.celda_cuerpo);
            vacio.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
            fila.addView(vacio);
        }
        tabla.addView(fila);
    }

}

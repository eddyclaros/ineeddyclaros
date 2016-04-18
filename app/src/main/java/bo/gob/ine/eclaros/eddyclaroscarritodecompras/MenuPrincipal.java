package bo.gob.ine.eclaros.eddyclaroscarritodecompras; /**
 * Created by Pc 12 on 17/04/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.Toast;

import bo.gob.ine.eclaros.eddyclaroscarritodecompras.R;

public class MenuPrincipal extends AppCompatActivity {
    private ImageButton btnAddUsuario;
    private ImageButton btnAddProducto;
    private ImageButton btnAddVenta;
    private ImageButton btnSalir;

    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        btnAddProducto=(ImageButton)findViewById(R.id.btnAddProducto);
        btnAddUsuario=(ImageButton)findViewById(R.id.btnAddUsuario);
        btnAddVenta=(ImageButton)findViewById(R.id.btnAddVenta);
        btnSalir=(ImageButton)findViewById(R.id.btnSalir);



        Intent b=getIntent();
        String[] datos_recibidos=new String[2];
        datos_recibidos=b.getStringArrayExtra("datos_usuario");


        Toast.makeText(this,"Bienvenido "+datos_recibidos[0],Toast.LENGTH_SHORT).show();

    }

}

package cl.ejemplos.listadecompras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cl.ejemplos.listadecompras.modelo.ComprasDatabaseHelper;
import cl.ejemplos.listadecompras.modelo.ListaDeCompras;
import cl.ejemplos.listadecompras.modelo.Producto;

public class NuevoProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        Button btnIngresarProducto=(Button) findViewById(R.id.btnIngresarProducto);
        btnIngresarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresarProducto(view);
            }
        });
    }

    public void ingresarProducto(View view)
    {
        ComprasDatabaseHelper helper=new ComprasDatabaseHelper(this);
        String nombre=((TextView)findViewById(R.id.ingresarNombre)).getText().toString();
        String cantidadStr=((TextView)findViewById(R.id.ingresarCantidad)).getText().toString();
        String unidad=((Spinner)findViewById(R.id.ingresarUnidad)).getSelectedItem().toString();
        String unidadNueva=((TextView)findViewById(R.id.otraUnidad)).getText().toString();

        int cantidad=0;
        try {
            cantidad=Integer.parseInt(cantidadStr);
        }
        catch (NumberFormatException ex)
        {
            Toast.makeText(this,"Debe ingresar un número en la cantidad",Toast.LENGTH_SHORT).show();
        }
        if(cantidad > 0)
        {
            Producto producto;
            if(unidad.equals("Otros") && !(unidadNueva.isEmpty()))
            {
                producto=new Producto(nombre,cantidad,unidadNueva);
            }
            else
            {
                producto=new Producto(nombre,cantidad,unidad);
            }
            //No hay errores

            helper.ingresarProducto(producto); //Ingresamos el producto a la base de datos
            finish();
        }
        else
        {
            Toast.makeText(this,"Ingrese una cantidad mayor a cero",Toast.LENGTH_SHORT).show();
        }
    }
}
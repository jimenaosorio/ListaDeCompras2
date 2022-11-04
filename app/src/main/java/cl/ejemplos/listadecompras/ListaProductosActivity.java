package cl.ejemplos.listadecompras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cl.ejemplos.listadecompras.modelo.ComprasDatabaseHelper;
import cl.ejemplos.listadecompras.modelo.ListaDeCompras;
import cl.ejemplos.listadecompras.modelo.Producto;

public class ListaProductosActivity extends ListActivity {
    ListView lista;
    ComprasDatabaseHelper helper=new ComprasDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        cargarLista();
    }

    public void cargarLista()
    {
        lista=getListView();
        ArrayList<Producto> productos= helper.listaProductos(); //Cargamos la lista desde la base de datos
        ArrayAdapter<Producto> listaAdapter=new ArrayAdapter<Producto>(
                this, android.R.layout.simple_list_item_1, productos
        );
        lista.setAdapter(listaAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Leer el elemento de la lista que se seleccionó
                Object obj=lista.getItemAtPosition(i);
                //Transformamos la línea a String
                String linea=obj.toString();
                //Obtenemos el nombre separando el String en un arreglo de tipo String
                String[] separar=linea.split(":");
                //Llamar a DetallesActivity
                Intent intent=new Intent(ListaProductosActivity.this,DetallesActivity.class);
                //Enviamos el nombre del producto
                intent.putExtra("nombreProducto",separar[0]);
                startActivityForResult(intent,1);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                cargarLista();
            }
        }
    }
}
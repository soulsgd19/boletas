package probando.apli;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Funcionario extends AppCompatActivity {

    private TextView etidentificacion, etrango,etnombre_func;
    private Spinner spidentificacion;
    private EditText edid,edrango,ednombre;
    private String rango,id,nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_funcionario);

        spidentificacion = findViewById (R.id.spfunc);
        etrango = findViewById (R.id.txt_rango);
        etnombre_func = findViewById (R.id.txt_nombre_func);
        edid = findViewById (R.id.edtid);
        edrango = findViewById (R.id.edrango);
        ednombre = findViewById (R.id.ednombre);










        bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();

        Cursor fila = BaseDeDatos.rawQuery ("select id_funcionario from funcionario ", null);
        int i = 0;
        ArrayList opciones = new ArrayList ();


        for (fila.moveToFirst ();!fila.isAfterLast ();fila.moveToNext ()){

            int a = fila.getPosition ();


            opciones.add ( fila.getString (0));



        }

        String [] vacia= new String[opciones.size ()];
        opciones.toArray (vacia);



        ArrayAdapter <String> adapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, vacia);
        spidentificacion.setAdapter (adapter);




    }

   public void seleccion(View view){
        bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();


        String posicion;
        posicion=spidentificacion.getSelectedItem ().toString ();

        String sql= "select nomb_funcionario,rango from funcionario where id_funcionario ='" + posicion + "';";;

       Cursor datos = BaseDeDatos.rawQuery (sql, null);




       if (datos.moveToFirst()) {

           etrango.setText (datos.getString (0));
           etnombre_func.setText (datos.getString (1));


       } else

           Toast.makeText(this, "No existe ningún usuario con ese dni",

                   Toast.LENGTH_SHORT).show();



   }

   public void añadir(View view){

       bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
       SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

       nombre=ednombre.getText ().toString ();
       rango=edrango.getText ().toString ();
       id=edid.getText ().toString ();
       ContentValues registro= new ContentValues();

       if (!nombre.isEmpty () && !rango.isEmpty () && !id.isEmpty ()){

           registro.put("nomb_funcionario",nombre);
       registro.put("rango",rango);
       registro.put("id_funcionario",id);
       BaseDeDatos.insert("funcionario",null , registro);

       BaseDeDatos.close();
           Intent siguiente = new Intent (this, Funcionario.class);
           startActivity (siguiente);
       }else{
           Toast.makeText (this,"debe de selecionar un funcionario para porder continuar",Toast.LENGTH_LONG).show ();
       }

   }
   public void siguiente(View view){

        nombre=etnombre_func.getText ().toString ();
        rango=etrango.getText ().toString ();
        id=spidentificacion.getSelectedItem ().toString ();


        if (!nombre.isEmpty () && !rango.isEmpty () && !id.isEmpty ()){

            // metodo boton siguiente
            Intent siguiente = new Intent (this, Leyes.class);
            startActivity (siguiente);

        }else{
            Toast.makeText (this,"debe de selecionar un funcionario para porder continuar",Toast.LENGTH_LONG).show ();
        }

   }

    //metodo para regresar a la anterior activity
    public void anterior(View view) {
        Intent anterior = new Intent (this, Remolque.class);
        startActivity (anterior);
    }
}


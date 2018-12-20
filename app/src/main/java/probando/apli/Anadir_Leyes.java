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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


public class Anadir_Leyes extends AppCompatActivity {

    private Spinner spselec;
    private EditText etley,etdescripcion;
    private String ley, descripcion,seleccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_anadir__leyes);

        spselec=findViewById (R.id.sptp_ley);
        etley=findViewById (R.id.aña_ley);
        etdescripcion=findViewById (R.id.aña_descrip);



        String [] opciones= {"Articulo","Decreto"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spselec.setAdapter (adapter);

    }

    public void agregar(View view) {

        ley=etley.getText ().toString ();

        descripcion=etdescripcion.getText().toString ();

        String seleccion=spselec.getSelectedItem ().toString ();


        if (seleccion.equals ("Articulo")){
            if(!ley.isEmpty() && !descripcion.isEmpty()){
            bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
            SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();



                ContentValues registro= new ContentValues();

                registro.put("articulo",ley);
                registro.put("descripcion",descripcion);
                BaseDeDatos.insert("Ley",null , registro);

                BaseDeDatos.close();

                Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();
                //metodo boton siguiente
                Intent siguiente = new Intent (this, Leyes.class);
                startActivity (siguiente);


        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();



        }
        }
        if (seleccion.equals ("Decreto") ) {
            if (!ley.isEmpty () && !descripcion.isEmpty ()){
            bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();

            ContentValues registro1 = new ContentValues ();

String sql =  "insert into Leyd (decreto,descripcion_d) values ('" + ley + "','"+descripcion+"');";;
                BaseDeDatos.execSQL (sql);

                BaseDeDatos.close ();

                Toast.makeText (this, "registo exitoso"+ley+descripcion, Toast.LENGTH_SHORT).show ();



              //metodo para regresar
                Intent siguiente = new Intent (this, Leyes.class);
                startActivity (siguiente);
            }
        } else {
            Toast.makeText (this, "debe llenar todos los campos para poder continuar", Toast.LENGTH_SHORT).show ();

        }

    }

}
package probando.apli;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.*;
import android.R.*;


public class Direccion_ciudadano extends AppCompatActivity {

    private EditText etlugar,etnombre,etdireccion,etestado,etminicipio,etparroquia,etcondicion;
    private String lugar;
    private String nombre;
    private String direccion;
    private String estado;
    private String municipio;
    private String parroquia;
    private String condicion;
    public Direccion_ciudadano() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_direccion_ciudadano);

        etlugar=findViewById (R.id.txt_lugar);
        etnombre= findViewById (R.id.txt_nombre);
        etdireccion= findViewById (R.id.txt_dir);
        etestado= findViewById (R.id.txt_estado);
        etminicipio= findViewById (R.id.txt_municipio);
        etparroquia= findViewById (R.id.txt_parroquia);
        etcondicion= findViewById (R.id.txt_condición);
    }
    //metodo para pasar los valores a la base de datos
    public void guardar3(View view){
        bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

        lugar = etlugar.getText ().toString ();
        nombre = etnombre.getText ().toString ();
        direccion = etdireccion.getText ().toString ();
        estado = etestado.getText ().toString ();
        municipio = etminicipio.getText ().toString ();
        parroquia = etparroquia.getText ().toString ();
        condicion = etcondicion.getText ().toString ();

        if (!nombre.isEmpty () && !direccion.isEmpty () && !estado.isEmpty ()
                && !municipio.isEmpty () && !parroquia.isEmpty () && !condicion.isEmpty ()){

            ContentValues registro= new ContentValues ();

            registro.put ("lugar",lugar);
            registro.put ("nomb_ciudadano",nombre);
            registro.put ("direc_ciudadano",direccion);
            registro.put ("estado",estado);
            registro.put ("municipio",municipio);
            registro.put ("parroquia",parroquia);
            registro.put ("condicion",condicion);

            BaseDeDatos.insert("boletas",null , registro);

            BaseDeDatos.close();

            Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();
            // metodo boton siguiente
            Intent siguiente = new Intent (this, Vehiculo.class);
            startActivity (siguiente);
        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();
        }

    }

    //metodo para regresar a la anterior activity
    public void anterior(View view) {
        Intent anterior = new Intent (this, Datos_del_Ciudadano.class);
        startActivity (anterior);
    }
}

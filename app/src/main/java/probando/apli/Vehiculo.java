package probando.apli;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.*;

import android.R.*;

public class Vehiculo extends AppCompatActivity {

    private Spinner spinner_vehiculo;
    private EditText etplaca,etmodelo,etmarca,etcolor,etaño,etserial;
    private String placa,modelo,marca,color,año_string,serial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_vehiculo);

        etplaca=findViewById (R.id.txt_placa);
        etmodelo = findViewById (R.id.txt_modelo);
        etmarca= findViewById (R.id.txt_marca);
        etcolor= findViewById (R.id.txt_color);
        etaño= findViewById (R.id.txt_año);
        etserial= findViewById (R.id.txt_serial_v);

        spinner_vehiculo=findViewById (R.id.spinner_tipo);

        String [] opciones= {"Autobus","Automovil","Moto"};
        ArrayAdapter <String> adapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner_vehiculo.setAdapter (adapter);
    }
    //metodo para guardar en la base de datos
    public void guardar4(View view){
        bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

        placa= etplaca.getText ().toString ();
        modelo= etmodelo.getText ().toString ();
        marca= etmarca.getText ().toString ();
        color= etmodelo.getText ().toString ();

        año_string= etaño.getText ().toString ();

        int año_int=Integer.parseInt (año_string);

        serial= etserial.getText ().toString ();

        String seleccion=spinner_vehiculo.getSelectedItem ().toString ();

        if (!placa.isEmpty () && !modelo.isEmpty () && !marca.isEmpty ()
                && !color.isEmpty () && !año_string.isEmpty () && !serial.isEmpty ()
                && !seleccion.isEmpty ()){

            ContentValues registro= new ContentValues ();

            registro.put ("placa",placa);
            registro.put ("marca",marca);
            registro.put ("modelo",modelo);
            registro.put ("tipo_vehiculo",seleccion);
            registro.put ("color",color);
            registro.put ("año",año_int);
            registro.put ("s_carroceria",serial);

            BaseDeDatos.insert("boletas",null , registro);

            BaseDeDatos.close();

            Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();
            // metodo boton siguiente
            Intent siguiente = new Intent (this, Remolque.class);
            startActivity (siguiente);
        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();
        }
    }
    //metodo para regresar a la anterior activity
    public void anterior(View view) {
        Intent anterior = new Intent (this, Direccion_ciudadano.class);
        startActivity (anterior);
    }
}

package probando.apli;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.*;
import android.R.*;


import java.text.BreakIterator;

public class Datos_del_Ciudadano extends AppCompatActivity {

    private EditText edt_cedula, edt_licencia, edt_telefono;
    private String cedula, licencia, telefono;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private int grado = 0;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_del__ciudadano);
        edt_cedula = findViewById(R.id.txt_cedula);
        edt_licencia = findViewById(R.id.txt_licencia);
        edt_telefono = findViewById(R.id.txt_telefono);
        rb1 = findViewById(R.id.rb_grado1);
        rb2 = findViewById(R.id.rb_grado2);
        rb3 = findViewById(R.id.rb_grado3);
        rb4 = findViewById(R.id.rb_grado4);
        rb5 = findViewById(R.id.rb_grado5);
        tv1 = findViewById(R.id.tv1);

    }


    //metodo para pasar los valores a la base de datos
    public void guardar2(View view){
        bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();



        cedula = edt_cedula.getText().toString();
        licencia = edt_licencia.getText().toString();

        if (rb1.isChecked() == true) {
            grado = 1;
            String mostrar=String.valueOf(grado);
            tv1.setText(mostrar);
        }
        if (rb2.isChecked() == true) {
            grado = 2;
            String mostrar=String.valueOf(grado);
            tv1.setText(mostrar);
        }
        if (rb3.isChecked() == true) {
            grado = 3;
            String mostrar=String.valueOf(grado);
            tv1.setText(mostrar);
        }
        if (rb4.isChecked() == true) {
            grado = 4;
            String mostrar=String.valueOf(grado);
            tv1.setText(mostrar);
        }
        if (rb5.isChecked ()) {
            grado = 5;
            String mostrar=String.valueOf(grado);
            tv1.setText(mostrar);
        }

        telefono = edt_telefono.getText().toString();

        if(!cedula.isEmpty() && !licencia.isEmpty() && grado>0 ){
            ContentValues registro= new ContentValues();

            registro.put("n_cedula",cedula);
            registro.put("licencia",licencia);
            registro.put("grado_licencia",grado);
            BaseDeDatos.insert("boletas",null , registro);

            BaseDeDatos.close();

            Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();
            //metodo boton siguiente
            Intent siguiente = new Intent (this, Direccion_ciudadano.class);
            startActivity (siguiente);
        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para regresar a la anterior activity
    public void anterior(View view){
        Intent anterior= new Intent(this, MainActivity.class);
        startActivity(anterior);
    }



}

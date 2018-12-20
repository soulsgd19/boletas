package probando.apli;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.*;

import android.R.*;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button bfecha,bhora;
    private TextView efecha,ehora;
    private int dia=0,mes=0,ano=0,hora=0,min=0;
    private EditText et_descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bfecha=(Button)findViewById(R.id.btn_fecha);
        bhora=(Button)findViewById(R.id.btn_hora);
        efecha=(TextView) this.findViewById(R.id.txt_fecha);
        ehora=(TextView) this.findViewById(R.id.txt_hora);
        et_descripcion=(EditText)findViewById(R.id.txt_descripcion);


    }
    //metodo para agrear la fecha
    public void fecha(View v){

            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH );
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(month+1)+"/"+year );
                }
            }
            ,dia,mes,ano);
            datePickerDialog.show();




    }
    //metodo para agregar la hora
    public void hora(View view){

            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            min=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                   if (minute<10){
                       ehora.setText(hourOfDay+":0"+minute);
                   }else{
                       ehora.setText(hourOfDay+":"+minute);
                   }
                }
            },hora,min,true);
            timePickerDialog.show ();

    }

    //metodo para agregar la descripcion, hora y fecha ala base de datos.
    public void guardar(View view){
        bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

        String descripcion= et_descripcion.getText().toString();
        String fecha= efecha.getText().toString();
        String hora= ehora.getText ().toString ();

        if (!descripcion.isEmpty() && !fecha.isEmpty() && !hora.isEmpty () ){
            ContentValues registro= new ContentValues();

            registro.put("descripcion", descripcion);
            registro.put("fecha",fecha);
            registro.put ("hora",hora);

            BaseDeDatos.insert("boletas",null , registro);

            BaseDeDatos.close();

            Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();

            //metodo boton siguiente
            Intent siguiente= new Intent(this,Datos_del_Ciudadano.class);
            startActivity(siguiente);

        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();
        }
    }


}

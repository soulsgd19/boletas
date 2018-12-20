package probando.apli;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.*;
import android.R.*;

import java.util.Calendar;

public class Remolque extends AppCompatActivity {

    private Button bfecha,bhora;
    private EditText etremolque,etestacionamiento,etcompadecer;
    private TextView tvfecha,tvhora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_remolque);

        bfecha=findViewById(R.id.btn_fecha_cita);
        bhora=findViewById(R.id.btn_hora_cita);
        tvfecha=findViewById(R.id.txt_fecha_cita);
        tvhora=findViewById(R.id.txt_hora_cita);
        etremolque=findViewById(R.id.txt_remolque);
        etestacionamiento=findViewById(R.id.txt_estacionamiento);
        etcompadecer=findViewById(R.id.txt_compadecer);
    }
    //metodo para agrear la fecha
    public void fecha(View v){

            final Calendar c= Calendar.getInstance();
            int dia=c.get(Calendar.DAY_OF_MONTH );
           int mes=c.get(Calendar.MONTH);
           int ano=c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvfecha.setText(dayOfMonth+"/"+(month+1)+"/"+year );
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.show();




    }
    // metodo para agragar la hora
    public void hora(View view){

            final Calendar x= Calendar.getInstance();
            int hora=x.get(Calendar.HOUR_OF_DAY);
            int  min=x.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (minute<10){
                        tvhora.setText(hourOfDay+":0"+minute);
                    }else{
                        tvhora.setText(hourOfDay+":"+minute);
                    }

                }
            },hora,min,false);
            timePickerDialog.show ();

    }
    //metodo para guardar los valores en la base de datos
    public void guardar5(View view){
        bdboletas admin= new bdboletas(this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

        String remolque= etremolque.getText().toString();
        String estacionamiento= etestacionamiento.getText().toString();
        String compadecer= etcompadecer.getText().toString();
        String fecha= tvfecha.getText().toString();
        String hora= tvhora.getText ().toString ();

        if (!remolque.isEmpty () && !estacionamiento.isEmpty () && !compadecer.isEmpty ()
                && !fecha.isEmpty () && !hora.isEmpty () ){

            ContentValues registro= new ContentValues ();

            registro.put ("remolque",remolque);
            registro.put ("estacionamiento",estacionamiento);
            registro.put ("compadecer",compadecer);
            registro.put ("fecha_citado",fecha);
            registro.put ("hora_citado",hora);

            BaseDeDatos.insert("boletas",null , registro);

            BaseDeDatos.close();

            Toast.makeText(this,"registo exitoso",Toast.LENGTH_SHORT).show();
            // metodo boton siguiente
            Intent siguiente = new Intent (this, Funcionario.class);
            startActivity (siguiente);
        }else{
            Toast.makeText(this,"debe llenar todos los campos para poder continuar",Toast.LENGTH_SHORT).show();
        }
    }
    //metodo para regresar a la anterior activity
    public void anterior(View view) {
        Intent anterior = new Intent (this, Vehiculo.class);
        startActivity (anterior);
    }
}

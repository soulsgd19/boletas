package probando.apli;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.PdfBorderArray;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfLayer;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.Templates;

import harmony.java.awt.Color;

public class Leyes extends AppCompatActivity  {
    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";

    private RadioButton rbarticulo, rbdecreto;
    private EditText etvalor_ut,etut_imp;
    private Spinner spley;
    private TextView etdescripción,etmonto;
    private double valorut,utimpuestas,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_leyes);

        rbarticulo=findViewById (R.id.rbArticulo);
        rbdecreto=findViewById (R.id.rbDecreto);
        etdescripción=findViewById (R.id.txt_descrip_ley);
        etvalor_ut=findViewById (R.id.txt_valor);
        etut_imp=findViewById (R.id.txt_ut);
        etmonto=findViewById (R.id.txt_monto);
        spley=findViewById (R.id.spLey);






    }

    public void añadir(View view){
        Intent siguiente = new Intent (this, Anadir_Leyes.class);
        startActivity (siguiente);
    }


    public void boleta(View view){

        bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();

        Cursor fila = BaseDeDatos.rawQuery ("select decreto from Leyd ", null);
        int i = 0;
        ArrayList opciones = new ArrayList ();


        for (fila.moveToFirst ();!fila.isAfterLast ();fila.moveToNext ()){

            int a = fila.getPosition ();


            opciones.add ( fila.getString (0));



        }





        String [] vacia= new String[opciones.size ()];
        opciones.toArray (vacia);


        ArrayAdapter<String> adapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, vacia);
        spley.setAdapter (adapter);


        BaseDeDatos.close ();

    }



    public void prueba(View view){

            bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();

            Cursor fila = BaseDeDatos.rawQuery ("select articulo from Ley ", null);
            int i = 0;
            ArrayList opciones = new ArrayList ();


            for (fila.moveToFirst ();!fila.isAfterLast ();fila.moveToNext ()){

                int a = fila.getPosition ();


                opciones.add ( fila.getString (0));



            }





            String [] vacia= new String[opciones.size ()];
            opciones.toArray (vacia);


            ArrayAdapter<String> adapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, vacia);
            spley.setAdapter (adapter);



            BaseDeDatos.close ();

    }

    public void seleccion(View view){
        bdboletas admin = new bdboletas (this, "Boleta de Transito", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase ();


        String posicion;
        posicion=spley.getSelectedItem ().toString ();

     if(rbarticulo.isChecked ()) {
         String sql = "select descripcion from Ley where articulo ='" + posicion + "';";
         ;

         Cursor datos = BaseDeDatos.rawQuery (sql, null);


         if (datos.moveToFirst ()) {

             etdescripción.setText (datos.getString (0));


         } else

             Toast.makeText (this, "No existe ningún usuario con ese dni",

                     Toast.LENGTH_SHORT).show ();


     }

     if (rbdecreto.isChecked ()) {
        String sql = "select descripcion_d from Leyd where decreto ='" + posicion + "';";
         ;

        Cursor datos = BaseDeDatos.rawQuery (sql, null);


         if (datos.moveToFirst ()) {

             etdescripción.setText (datos.getString (0));


         } else

             Toast.makeText (this, "No existe ningún usuario con ese dni",

                     Toast.LENGTH_SHORT).show ();


     }
    }




    public void pagar(View view){
        String vlut=etvalor_ut.getText ().toString ();
        String utimp=etut_imp.getText ().toString ();

        if (!vlut.isEmpty () && !utimp.isEmpty ()) {
            valorut = Double.parseDouble (vlut);
            utimpuestas = Double.parseDouble (utimp);

            total = valorut * utimpuestas;

            etmonto.setText (String.valueOf (total));
        }else{
            Toast.makeText (this,"primero debe ingrasar el valor de la ut y las ut impuestas",Toast.LENGTH_LONG).show ();
        }
    }



    public void siguiente(View view) {

        Intent siguiente = new Intent (this, pdf.class);
        startActivity (siguiente);

    }

    //metodo para regresar a la anterior activity
    public void anterior(View view) {
        Intent anterior = new Intent (this, Funcionario.class);
        startActivity (anterior);
    }

public void finals(View view){

    Document documento = new Document();

    try {

        File f = crearFichero(NOMBRE_DOCUMENTO);

        FileOutputStream ficheroPdf = new FileOutputStream (
                f.getAbsolutePath ()
        );

        PdfWriter writer = PdfWriter.getInstance(documento,ficheroPdf);


        Font font =  new
                Font ();
        font.setSize (9);


        HeaderFooter pie = new HeaderFooter (new Phrase ("La competenia y pago de la multa extingue la misma.\n" +
                "Si la multa no es pagada por el infractor en la oportunidad prevista en la Ley, la misma causara intereses de mora\n" +
                "El sancionado podra ejercer los recursos establecidos por la ley.\n" +
                "La planilla de multa impuesta por la infraccion, tiene el caracter de Titulo Ejecutivo",font),false);

pie.setAlignment (pie.ALIGN_LEFT);

        documento.setFooter (pie);


Paragraph membrete = new Paragraph ("\"REPÚBLICA BOLIVARIANA DE VENEZUELA\\n\" +\n" +
        "        \"ALCALDIA BOLIVARIANA DEL MUNICIPIO \\\"JUAN GERMAN ROSCIO NIEVES\\\"\\n\" +\n" +
        "        \"INSTITUTO AUTONOMO DE POLICIA\" +\n" +
        "        \"ADMINISTRATIVA Y DEL TRANSITO MUNICIPAL\\n\" +\n" +
        "        \"DIRECCION DE VIGILANCIA Y TRANSPORTE TERRERSTRE\\n\" +\n" +
        "        \"RIF.:G-20001974-3\"",font);
membrete.setAlignment (membrete.ALIGN_CENTER);
        documento.open ();
        documento.add (membrete);


        PdfPTable tabla = new PdfPTable(3);
        tabla.addCell ("FECHA: \n ");tabla.addCell ("HORA:\n ");tabla.addCell ("MOTIVO:\n "+"");

        PdfPTable tabla1 = new PdfPTable(2);
        tabla1.addCell ("V Cedula:\n");tabla1.addCell ("Licencia/Grado:\n");

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.addCell ("Telefono:\n");

        PdfPTable tabla3 = new PdfPTable(2);
        tabla3.addCell ("Telefono:\n");

        PdfPTable tabla4 = new PdfPTable(2);
        tabla4.addCell ("Lugar:\n");tabla4.addCell ("Al ciudadano\n");

        PdfPTable tabla5 = new PdfPTable(1);
        tabla5.addCell ("Direccion Del Ciudadano\n");



        PdfPTable tabla6 = new PdfPTable(3);
        tabla6.addCell ("Estado:\n");tabla6.addCell ("Municipio:\n");tabla6.addCell ("Parroquia:\n");

        PdfPTable tabla7 = new PdfPTable(1);
        tabla7.addCell ("En su condición:\n");

        PdfPTable tabla8 = new PdfPTable(3);
        tabla8.addCell ("Color:\n");tabla8.addCell ("AÑO:\n");tabla8.addCell ("S/CARROCERÍA:\n ");tabla8.addCell ("REMOLQUE:\n ");


        PdfPTable tabla9 = new PdfPTable(1);

        tabla9.addCell ("Estacionamiento:\n");

        PdfPTable tabla10 = new PdfPTable(3);
        tabla10.addCell ("EL CITADO DEBE COMPADECER ANTE:\n ");tabla10.addCell ("EN FECHA:\n ");tabla10.addCell ("HORA:\n");

        PdfPTable tabla11 = new PdfPTable(1);
        Paragraph text=new Paragraph ("DATOS DEL FUNCIONARIO");
        text.setAlignment (text.ALIGN_CENTER);
        tabla11.addCell (text);

        PdfPTable tabla12 = new PdfPTable(3);
        tabla12.addCell ("N- DE IDENTIFICACIÓN:\n ");tabla12.addCell ("RANGO\n");tabla12.addCell ("NOMBRE Y APELLIDO:\n ");

        PdfPTable tabla13 = new PdfPTable(3);
        tabla13.addCell ("INFRACCIÓN:\n ");tabla13.addCell ("NÚMERO:\n ");tabla13.addCell ("DESCRIPCIÓN: \n ");


        PdfPTable tabla14 = new PdfPTable(3);

        tabla14.addCell ("VALOR DE LA U.T.: \n ");tabla14.addCell ("U.T. IMPUESTAS:  \n");tabla14.addCell ("MONTO EN BOLIVARES:  \n ");

        PdfPTable tabla15 = new PdfPTable(1);
        Phrase text1 = new Phrase ("___________________\n "+"Firma del ciudadano");

        Phrase text2 = new Phrase ("______________________ \n      y   cedula de identidad");
        tabla15.addCell ("\n");
        tabla15.addCell (text1);
        tabla15.addCell (text2);



        documento.add(tabla);
        documento.add (tabla1);
        documento.add (tabla2);
        documento.add (tabla3);
        documento.add (tabla4);
        documento.add (tabla5);
        documento.add (tabla6);
        documento.add (tabla7);
        documento.add (tabla8);
        documento.add (tabla9);
        documento.add (tabla10);
        documento.add (tabla11);
        documento.add (tabla12);
        documento.add (tabla13);
        documento.add (tabla14);
        documento.add (tabla15);





    } catch (IOException e) {
        Log.e (ETIQUETA_ERROR, e.getMessage ());
    } catch (DocumentException e) {
        Log.e (ETIQUETA_ERROR, e.getMessage ());
    }finally {
        documento.close ();

    }






    File pdfFile = new File(Environment.getExternalStorageDirectory(),"/Download/MiPdf/prueba.pdf" );//File path
    if (pdfFile.exists()){ //Revisa si el archivo existe!
        Uri path = Uri.fromFile(pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //define el tipo de archivo
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
        //Inicia pdf viewer
        startActivity(intent);
    } else {
        Toast.makeText(getApplicationContext(), "No existe archivo! ", Toast.LENGTH_SHORT).show();
    }

















}

    public static File crearFichero(String nombrefichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File (ruta,nombrefichero);
        return  fichero;
    }











    public static File getRuta(){
        File ruta=null;
        if (Environment.MEDIA_MOUNTED.equals (Environment.getExternalStorageState ())){
            ruta = new File (
                    Environment
                            .getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);
            if (ruta != null){
                if (!ruta.mkdirs ()){
                    if (!ruta.exists ()){
                        return null;
                    }

                }
            }else {

            }

        }

        return ruta;
    }


}


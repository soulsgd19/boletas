package probando.apli;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bdboletas extends SQLiteOpenHelper {

    private String rango;
    private String nomb_funcionario;

    public bdboletas(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase bdboletas) {
        bdboletas.execSQL("create table boletas(num_boleta INTEGER PRIMARY KEY, descripcion text, fecha text," +
                "hora text, lugar text, nomb_ciudadano text, " +
                " n_cedula int, telefono int, licencia tex,grado_licencia int, direc_ciudadano text, estado text, municipio text," +
                "parroquia text, condicion text, placa text, marca text, modelo text, tipo_vehiculo text,color text," +
                "año int, s_carroceria text, remolque text, estacionamiento text, compadecer text, fecha_citado text, hora_citado text," +
                "ut_impuesta int, valor_ut real )");
        /*bdboletas.execSQL("create table banco(id_banco int primary key, nombre_banco text, N_cuenta int, tipo_cuenta boolean)");*/
        bdboletas.execSQL("create table Ley( articulo text, descripcion text)");
        bdboletas.execSQL("create table Leyd(decreto text, descripcion_d text)");
        bdboletas.execSQL("create table funcionario(id_funcionario text , nomb_funcionario text, rango text, estado boolean)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public String getnomb_funcionario(){
        return this.nomb_funcionario;
    }
    public  void setnomb_funcionario (String nomb_funcionasrio){
        this.nomb_funcionario= nomb_funcionasrio;
    }

    public String getrango( ){
        return this.rango;
    }

    public void setrango(String rango){
        this.rango = rango;
    }

public boolean consulta(boolean consul){



        return consul;

}
}

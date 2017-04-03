package sergio.es.empleados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Empleados";
    private static final int DATABASE_VERSION = 1;

    public static class TablaEmpleados {
        public static String TABLA_EMPLEADOS = "Empleados";
        public static String COLUMNA_ID = "id";
        public static String COLUMNA_NOMBRE = "nombre";
        public static String COLUMNA_CARGO = "cargo";
        public static String COLUMNA_TELEFONO = "telefono";
        public static String COLUMNA_CORREO = "email";
    }

    private static final String DATABASE_CREATE = "create table " + TablaEmpleados.TABLA_EMPLEADOS + "(" + TablaEmpleados.COLUMNA_ID + " integer primary key autoincrement, " + TablaEmpleados.COLUMNA_NOMBRE + " text not null, " + TablaEmpleados.COLUMNA_CARGO + " text not null, " + TablaEmpleados.COLUMNA_TELEFONO + " text not null, " + TablaEmpleados.COLUMNA_CORREO + " text not null);";

    public MySQLiteOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creamos la base de datos con los datos.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("delete table if exists " +  TablaEmpleados.TABLA_EMPLEADOS);
        onCreate(db);
    }
}

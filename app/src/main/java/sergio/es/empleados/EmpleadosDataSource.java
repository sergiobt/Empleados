package sergio.es.empleados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sergio.es.empleados.MySQLiteOpenHelper.TablaEmpleados;
public class EmpleadosDataSource {

    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;
    private String [] columnas = {TablaEmpleados.COLUMNA_ID, TablaEmpleados.COLUMNA_NOMBRE, TablaEmpleados.COLUMNA_CARGO, TablaEmpleados.COLUMNA_TELEFONO, TablaEmpleados.COLUMNA_CORREO};

    public EmpleadosDataSource(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    // Metodo para acceder a la base de datos en forma de escritura.
    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    // Metodo para cerrar la conexion con la base de datos.
    public void close() {
        dbHelper.close();
    }

    // En este método implementaremos lo necesario para crear un nuevo registro
    // en la base da datos. Tiene como argumentos variables de tipo String
    // que contienen los datos del empleado.
    public void crearEmpleado(String nombre, String cargo, String telefono, String correo){
        ContentValues values = new ContentValues();

        values.put(TablaEmpleados.COLUMNA_NOMBRE, nombre);
        db.insert(TablaEmpleados.TABLA_EMPLEADOS, null, values);

        values.put(TablaEmpleados.COLUMNA_CARGO, cargo);
        db.insert(TablaEmpleados.TABLA_EMPLEADOS, null, values);

        values.put(TablaEmpleados.COLUMNA_TELEFONO, telefono);
        db.insert(TablaEmpleados.TABLA_EMPLEADOS, null, values);

        values.put(TablaEmpleados.COLUMNA_CORREO, correo);
        db.insert(TablaEmpleados.TABLA_EMPLEADOS, null, values);
    }

    // Este método será el encargado de obtener todas los empleados que tengamos
    // en la tabla “empleados”.
    public List<Empleado> getAllEmpleados() {
        List<Empleado> listaEmpleados = new ArrayList<Empleado>();

        Cursor cursor = db.query(TablaEmpleados.TABLA_EMPLEADOS, columnas, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Empleado nuevoEmpleado = cursorToEmpleado(cursor);
            listaEmpleados.add(nuevoEmpleado);
            cursor.moveToNext();
        }

        cursor.close();
        return listaEmpleados;
    }

    // Metodo para borrar un empleado de la base de datos
    public void borrarEmpleados(Empleado empleado) {
        long id = empleado.getId();
        db.delete(TablaEmpleados.TABLA_EMPLEADOS, TablaEmpleados.COLUMNA_ID + " = " + id, null);
    }

    private Empleado cursorToEmpleado (Cursor cursor) {
        Empleado empleado = new Empleado();
        empleado.setId(cursor.getLong(0));
        empleado.setNombre(cursor.getString(1));
        empleado.setCargo(cursor.getString(2));
        empleado.setTelefono(cursor.getString(3));
        empleado.setCorreo(cursor.getString(4));

        return empleado;
    }
}

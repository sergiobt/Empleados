package sergio.es.empleados;

import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmpleadosActivity extends AppCompatActivity {

    private int requestCode = 1;
    private ListView lvEmpleados;
    private EmpleadosDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        // Instanciamos EmpleadosDataSource para poder realizar acciones con la base de datos.
        dataSource = new EmpleadosDataSource(this);
        dataSource.open();

        // Instanciamos los elementos
        lvEmpleados = (ListView) findViewById(R.id.lvEmpleados);

        // Cargamos la lista de empleados disponibles
        List<Empleado> listaEmpleados = dataSource.getAllEmpleados();
        ArrayAdapter<Empleado> adapter = new ArrayAdapter<Empleado> (this, android.R.layout.simple_list_item_1, listaEmpleados);

        // Establecemos el adapter
        lvEmpleados.setAdapter(adapter);

        // Establecemos un listener para el evento de pulsacion.
        // Necesita un AdapterView
        lvEmpleados.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    // Este método será ejecutado cuando pulsemos el Button que definimos en el
    // XML de la UI. Se encargará de ejecutar el método
    // startActivityForResult(), ya que según lo que retorne la actividad que
    // llame actualizaremos el ListView o no.
    public void agregarEmpleado (View view) {
        Intent intent = new Intent(this, NuevoEmpleadoActivity.class);
        startActivityForResult(intent, requestCode);
    }

    // Este es el Listener que usaremos para capturar el evento de click sobre
    // un elemento de la ListView.
    public void onItemClick (final AdapterView<?> adapterView, View view, final int position, long id) {
        Empleado empleado = (Empleado) adapterView.getItemAtPosition(position);

        Intent intent = new Intent(this, VerEmpleadoActivity.class);
        intent.putExtra("nombre", empleado.getNombre());
        intent.putExtra("cargo", empleado.getCargo());
        intent.putExtra("telefono", empleado.getTelefono());
        intent.putExtra("correo", empleado.getCorreo());
        intent.putExtra("id", empleado.getId());
        startActivity(intent);
    }

    // Comprobaremos si la operación en la segunda Activity que crearemos a
    // continuación ha tenido éxito. Si ha tenido éxito, refrescamos la lista de
    // elementos.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Result", "Se ejecuta onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == this.requestCode && resultCode == RESULT_OK) {
            // Actualizar el adapter.
            dataSource.open();
            refrescarLista();
        }
    }
    // Este método se encarga de realizar una consulta a la base de datos para
    // cargar un objeto List, crear un ArrayAdapter nuevo y establecerlo.
    private void refrescarLista() {
        List<Empleado> listaEmpleados = dataSource.getAllEmpleados();
        ArrayAdapter<Empleado> adapter = new ArrayAdapter<Empleado>(this, android.R.layout.simple_list_item_1, listaEmpleados);
        lvEmpleados.setAdapter(adapter);

        // Cargamos la lista de Empleados disponiles
    }

    // Si la aplicacion se pausa por alguna razon cerramos la conexion con la base de datos.
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    // Si la aplicacion se resume, volvemos a abrir la conexion con la base de datos.
    protected void onResume(){
        dataSource.open();
        refrescarLista();
        super.onResume();
    }
}

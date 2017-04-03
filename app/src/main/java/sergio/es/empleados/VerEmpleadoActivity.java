package sergio.es.empleados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class VerEmpleadoActivity extends AppCompatActivity {

    TextView txtNombre;
    String nombre;
    TextView txtCargo;
    String cargo;
    TextView txtTelefono;
    String telefono;
    TextView txtCorreo;
    String correo;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_empleado);

        txtNombre = (TextView) findViewById(R.id.nombre);
        txtCargo = (TextView) findViewById(R.id.cargo);
        txtTelefono = (TextView) findViewById(R.id.telefono);
        txtCorreo = (TextView) findViewById(R.id.correo);

        Bundle extras = this.getIntent().getExtras();

        nombre = extras.getString("nombre");
        cargo = extras.getString("cargo");
        telefono = extras.getString("telefono");
        correo = extras.getString("correo");
        id = extras.getLong("id");

        txtNombre.setText(nombre);
        txtCargo.setText(cargo);
        txtTelefono.setText(telefono);
        txtCorreo.setText(correo);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflates the menu. This adds items to the action bar if it's present.
        getMenuInflater().inflate(R.menu.ver_empleado, menu);
        return true;
    }

    public void onClickLlamarEmpleado(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel: " + telefono));
        startActivity(intent);
    }

    public void correoEmpleado (View view) {
        String[] destinatario = {correo};
        enviar(destinatario, "", "");
    }

    private void enviar (String[] destinatario, String asunto, String mensaje) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }

    private void borraEmpleado(View view) {
        final EmpleadosDataSource dataSource = new EmpleadosDataSource(this);
        dataSource.open();

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Borrar Empleado").setMessage("¿Desea borrar este empleado?").setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface arg0, int argc1){
                Empleado empleado = new Empleado();

                empleado.setId(id);
                empleado.setNombre(nombre);
                empleado.setCargo(cargo);
                empleado.setTelefono(telefono);
                empleado.setCorreo(correo);

                dataSource.borrarEmpleados(empleado);
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            public void onClick (DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }
}

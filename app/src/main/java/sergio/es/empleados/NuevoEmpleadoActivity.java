package sergio.es.empleados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoEmpleadoActivity extends AppCompatActivity {

    private static int resultCode = 10;

    private Button btnAgregar;
    private EditText txtNombre;
    private EditText txtCargo;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private EmpleadosDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_empleado);

        dataSource = new EmpleadosDataSource(this);
        dataSource.open();

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtCargo = (EditText) findViewById(R.id.txtCargo);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String textoNombre = txtNombre.getText().toString();
                String textoCargo = txtCargo.getText().toString();
                String textoTelefono = txtTelefono.getText().toString();
                String textoCorreo = txtCorreo.getText().toString();

                if (textoNombre.length() != 0 && textoCargo.length() != 0 && textoTelefono.length() != 0 && textoCorreo.length() != 0) {
                    dataSource.crearEmpleado(textoNombre, textoCargo, textoTelefono, textoCorreo);
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No ha introducido texto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

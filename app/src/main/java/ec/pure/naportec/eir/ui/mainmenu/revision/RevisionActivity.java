package ec.pure.naportec.eir.ui.mainmenu.revision;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.remote.model.QrInspeccion;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.main.MainActivity;
import ec.pure.naportec.eir.utils.AppUtils;
import ec.pure.naportec.eir.utils.DialogoLista;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;
import ec.pure.naportec.eir.utils.VariasImageCameraActivity;
import ec.pure.naportec.eir.utils.qr.FullScannerActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RevisionActivity extends PersonalizadoToolBar {

    private static final String TAG = "eir." + RevisionActivity.class.getSimpleName();

    private static final String extra_lectura = "lecturaQr";

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    private List<String> lstNombreFoto;

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_lista = "extra_lista";
    private String mPlacaIngresada, mEventoSeleccionado;

    private EditText fecha, noEIR, noTransaccion, noEntrada;
    private TextView booking, buque, viaje, puerto, contenedor;
    private EditText comentario;
    private Button revisado, verLecturasDeCodigoQr;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    @Inject
    ViewModelFactory viewModelFactory;
    RevisionViewModel revisionViewModel;
    RevisionSyncViewModel revisionSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        revisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(RevisionViewModel.class);
        revisionSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(RevisionSyncViewModel.class);

        inspeccionEntity = revisionSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        dataInjection();
        setViews();
        ponerPlacaYevento();
        setear_datos_desde_inspeccionEntity();

        lstNombreFoto = new ArrayList<>();

        verLecturasDeCodigoQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogoLista dialogAgregar = DialogoLista.newInstance("LISTA DE CÓDIGOS QR", "Si desea borrar, seleccione el item y luego desliza a la izquierda");

                List<String> lstQr = new ArrayList<>();
                if (inspeccionEntity.getQr() != null) {
                    Bundle bundle = new Bundle();

                    for (QrInspeccion qr : inspeccionEntity.getQr()) {
                        lstQr.add(qr.getEiridrSello());
                    }

                    bundle.putStringArrayList(extra_lista, (ArrayList<String>) lstQr);
                    dialogAgregar.setArguments(bundle);
                }

                dialogAgregar.show(getSupportFragmentManager(), TAG);
            }
        });

        revisado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inspeccionEntity.setEiricbUuid(mEiricbUuid);
                inspeccionEntity.setEiricbObstecnico((comentario.getText().toString()));
                inspeccionEntity.setEiricbUsertecnico(Global.usuario);
                inspeccionEntity.setEiricbPtctecnico(Global.ptc);

                revisionViewModel.actualizarInspeccion(inspeccionEntity);
                revisionViewModel.obtenerRespuestaDeSubirInspeccionLiveData().observe(RevisionActivity.this, resource -> {
                    if (resource.isLoading()) {
                    } else if (resource.data != null) {

                        Toast.makeText(getApplicationContext(), resource.data.getMensaje(), Toast.LENGTH_LONG).show();

                        if (resource.data.getEstado() == 1)
                            finish();

                    } else handleErrorResponse();
                });
            }

            private void handleErrorResponse() {
            }
        });

    }

    public void setDatos(List<String> lstStringQr, boolean siGraba) {
        if (siGraba) {
            List<QrInspeccion> lstQr = new ArrayList<>();
            for (String sello : lstStringQr) {
                QrInspeccion qrInspeccion = new QrInspeccion();
                qrInspeccion.setEiridrSello(sello);

                lstQr.add(qrInspeccion);
            }
            // guardamos los codigos Qr
            // siempre se setea primero el id del entity para actualizar el entity
            inspeccionEntity.setEiricbUuid(mEiricbUuid);
            inspeccionEntity.setQr(lstQr);

            revisionSyncViewModel.actualizarInspeccion(inspeccionEntity);
        }

    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);
    }

    private void setViews() {
        fecha = findViewById(R.id.edtx_fecha);
        noEIR = findViewById(R.id.edtx_noEIR);
        noTransaccion = findViewById(R.id.edtx_noTransaccion);
        noEntrada = findViewById(R.id.edtx_noEntrada);
        booking = findViewById(R.id.edt_booking);
        buque = findViewById(R.id.edt_buque);
        viaje = findViewById(R.id.edt_viaje);
        puerto = findViewById(R.id.edt_puerto);
        contenedor = findViewById(R.id.edt_contenedor);
        comentario = findViewById(R.id.edt_comentario);
        verLecturasDeCodigoQr = findViewById(R.id.btn_ver_lecturas_qr);
        revisado = findViewById(R.id.btn_revisado);
    }

    public void setear_datos_desde_inspeccionEntity() {
        fecha.setText(inspeccionEntity.getEiricbCreatedAt());
        noEIR.setText(inspeccionEntity.getEiricbCodigo());
        noTransaccion.setText(inspeccionEntity.getEiricbNrotransaccion());
        noEntrada.setText(inspeccionEntity.getEiricbNroentrada());
        booking.setText(inspeccionEntity.getEiricbBlbooking());
        buque.setText(inspeccionEntity.getEiricbBuque());
        viaje.setText(inspeccionEntity.getEiricbViaje());
        puerto.setText(inspeccionEntity.getEiricbPuerto());
        contenedor.setText(inspeccionEntity.getEiricbContenedor());
        comentario.setText(inspeccionEntity.getEiricbObspatio());
    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    public void launchScannerQrActivity(View v) {
        launchActivity(FullScannerActivity.class);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivityForResult(intent, 1);
        }
    }

    public void launchAgregaFotosActivity(View view) {
        /*LAPI Intent intent = new Intent(this, AgregaFotosActivity.class);
        intent.putExtra("lstNombreFoto", (Serializable) lstNombreFoto);
        startActivityForResult(intent, 2);*/

        AppUtils.verificaSD_Directory("/eir_imagenes/");
        if (!AppUtils.bool_sdAccesoEscritura || !AppUtils.bool_sdDisponible)
            Toast.makeText(getApplicationContext(),
                    "Problemas con la Memoria Externa", Toast.LENGTH_SHORT).show();
        if (!AppUtils.bool_dirExist)
            Toast.makeText(getApplicationContext(),
                    "No se puede crear el Directorio de imagenes", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VariasImageCameraActivity.class);
        intent.putExtra(VariasImageCameraActivity.ARG_PANTALLA_ORIGEN, VariasImageCameraActivity.PANTALLA_DOS);
        intent.putExtra(VariasImageCameraActivity.ARG_PHOTO_NAME_BASE, "XYZ123" + "_");
        intent.putExtra(VariasImageCameraActivity.EXTRA_ENTIDAD_CODE, "XYZ123");
        intent.putExtra(VariasImageCameraActivity.EXTRA_TITULO, "Revisión");
        intent.putExtra(VariasImageCameraActivity.EXTRA_CONSULTA, false);

        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        startActivityForResult(intent, VariasImageCameraActivity.PANTALLA_DOS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                    String lecturaQr = data.getStringExtra(extra_lectura);
                    Toast.makeText(getApplicationContext(), "Se ha leído el código " + lecturaQr, Toast.LENGTH_LONG).show();

                    QrInspeccion qrNueva = new QrInspeccion();
                    qrNueva.setEiridrSello(lecturaQr);

                    List<QrInspeccion> lstQr = new ArrayList<>();
                    if (inspeccionEntity.getQr() != null) {
                        lstQr.addAll(inspeccionEntity.getQr());
                    }
                    lstQr.add(qrNueva);

                    // guardamos los codigos Qr
                    // siempre se setea primero el id del entity para actualizar el entity
                    inspeccionEntity.setEiricbUuid(mEiricbUuid);
                    inspeccionEntity.setQr(lstQr);

                    revisionSyncViewModel.actualizarInspeccion(inspeccionEntity);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    Toast.makeText(getApplicationContext(), "Sin lectura de código", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case (2): {
                if (resultCode == Activity.RESULT_OK) {
                    lstNombreFoto = data.getStringArrayListExtra("lstNombreFoto");
                    System.out.println("lstNombreFoto " + lstNombreFoto);
                }
            }
        }
    }

}

package ec.pure.naportec.eir.ui.mainmenu.patio;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDate;

import javax.inject.Inject;

public class PatioActivity extends PersonalizadoToolBar {

    private static final String TAG_PatioActivity = "eir." + PatioActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    private EditText fecha, noEIR, noTransaccion, noEntrada;
    private TextView booking, buque, viaje, puerto;
    private EditText contenedor, comentario;
    private Button asignar;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    @Inject
    ViewModelFactory viewModelFactory;
    PatioViewModel patioViewModel;
    PatioSyncViewModel patioSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        patioViewModel = ViewModelProviders.of(this, viewModelFactory).get(PatioViewModel.class);
        patioSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(PatioSyncViewModel.class);

        inspeccionEntity = patioSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patio);
        dataInjection();
        setViews();
        ponerPlacaYevento();
        setear_datos_desde_inspeccionEntity();

        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inspeccionEntity.setEiricbUuid(mEiricbUuid);
                inspeccionEntity.setEiricbContenedor(contenedor.getText().toString());
                inspeccionEntity.setEiricbObspatio(comentario.getText().toString());

                inspeccionEntity.setEiricbUserpatio(Global.usuario);
                inspeccionEntity.setEiricbPtcpatio(Global.ptc);

                patioViewModel.actualizarInspeccion(inspeccionEntity);
                patioViewModel.obtenerRespuestaDeSubirInspeccionLiveData().observe(PatioActivity.this, resource -> {
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
        asignar = findViewById(R.id.btn_asignar);
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

}

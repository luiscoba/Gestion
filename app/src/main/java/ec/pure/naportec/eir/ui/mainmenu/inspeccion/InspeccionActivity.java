package ec.pure.naportec.eir.ui.mainmenu.inspeccion;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.RespuestaDeSubirEirApiResponse;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.chassis.ChassisActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.ContenedorActivity;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos.SellosActivity;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class InspeccionActivity extends PersonalizadoToolBar {

    private static final String TAG_InspeccionActivity = "eir." + InspeccionActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_evento_int = "extra_evento_int";
    private String mPlacaIngresada, mEventoSeleccionado;
    private int mEventoSeleccionadoint;

    private Button encabezado, contenedor, chassis, sellos, llantas, descartar_eir, terminar_inspeccion;
    private EditText observacion;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    @Inject
    ViewModelFactory viewModelFactory;
    InspeccionViewModel inspeccionViewModel;
    InspeccionSyncViewModel inspeccionSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        inspeccionViewModel = ViewModelProviders.of(this, viewModelFactory).get(InspeccionViewModel.class);
        inspeccionSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(InspeccionSyncViewModel.class);

        inspeccionEntity = inspeccionSyncViewModel.obtenerInspeccion();
        if (inspeccionEntity != null) {
            mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
            observacion.setText(inspeccionEntity.getEiricbObserva2());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion);

        setViews();

        dataInjection();

        ponerPlacaYevento();


        encabezado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_EncabezadoActivity();
            }
        });
        contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_ContenedorActivity();
            }
        });
        chassis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_ChassisActivity();
            }
        });
        sellos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_SellosActivity();
            }
        });
        llantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_LlantasActivity();
            }
        });
        descartar_eir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        terminar_inspeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global.entro_a_encabezado == false) {
                    Toast.makeText(getApplicationContext(), "No se graba, porque no se ha ingresado datos en Encabezado", Toast.LENGTH_LONG).show();
                } else if (Global.entro_a_chassis == false) {
                    Toast.makeText(getApplicationContext(), "No se graba, porque no se ha ingresado datos en Chassis", Toast.LENGTH_LONG).show();
                } else if (Global.entro_a_sellos == false) {
                    Toast.makeText(getApplicationContext(), "No se graba, porque no se ha ingresado datos en Sellos", Toast.LENGTH_LONG).show();
                } else if (Global.entro_a_llantas == false) {
                    Toast.makeText(getApplicationContext(), "No se graba, porque no se ha ingresado datos en Llantas", Toast.LENGTH_LONG).show();
                } else {

                    inspeccionEntity = inspeccionSyncViewModel.obtenerInspeccion();
                    mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion

                    if (Global.ubicacion_seleccionada.equals("Banana Puerto"))
                        inspeccionEntity.setGenrciUuid1("63");//"Global.gate_seleccionado);

                    if (Global.ubicacion_seleccionada.equals("R9"))
                        inspeccionEntity.setGenrciUuid1("65");//"Global.gate_seleccionado);

                    if (Global.ubicacion_seleccionada.equals("Check Point"))
                        inspeccionEntity.setGenrciUuid1("67");//"Global.gate_seleccionado);

                    inspeccionEntity.setEiricbUser(Global.usuario);
                    inspeccionEntity.setEiricbPtc(Global.ptc);
                    inspeccionEntity.setEiricbObserva2(observacion.getText().toString());

                    inspeccionViewModel.actualizarInspeccion(inspeccionEntity);
                    inspeccionViewModel.obtenerRespuestaDeSubirInspeccionLiveData().observe(InspeccionActivity.this, resource -> {
                        if (resource.isLoading()) {
                        } else if (resource.data != null) {

                            Toast.makeText(getApplicationContext(), resource.data.getMensaje(), Toast.LENGTH_LONG).show();

                            if (resource.data.getEstado() == 1)
                                finish();

                        } else handleErrorResponse();
                    });
                }
            }

            private void handleErrorResponse() {
                System.out.println("log.error error de conexion ");
                System.out.println("log.error error de conexion ");
            }
        });

    }

    private void setViews() {
        encabezado = findViewById(R.id.btn_encabezado);
        contenedor = findViewById(R.id.btn_contenedor);
        chassis = findViewById(R.id.btn_chassis);
        sellos = findViewById(R.id.btn_sellos);
        llantas = findViewById(R.id.btn_llantas);

        observacion = findViewById(R.id.edt_observacion);

        descartar_eir = findViewById(R.id.btn_descartar_eir);
        terminar_inspeccion = findViewById(R.id.btn_terminar_inspeccion);
    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        mEventoSeleccionadoint = mIntent.getIntExtra(extra_evento_int, 0);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);
    }

    private void launch_EncabezadoActivity() {
        Intent intent = new Intent(this, EncabezadoActivity.class);
        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        intent.putExtra(extra_evento_int, mEventoSeleccionadoint);
        startActivity(intent);
    }

    private void launch_ContenedorActivity() {
        Intent intent = new Intent(this, ContenedorActivity.class);
        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        intent.putExtra(extra_evento_int, mEventoSeleccionadoint);
        startActivity(intent);
    }

    private void launch_ChassisActivity() {
        Intent intent = new Intent(this, ChassisActivity.class);
        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        intent.putExtra(extra_evento_int, mEventoSeleccionadoint);
        startActivity(intent);
    }

    private void launch_SellosActivity() {
        Intent intent = new Intent(this, SellosActivity.class);
        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        intent.putExtra(extra_evento_int, mEventoSeleccionadoint);
        startActivity(intent);
    }

    private void launch_LlantasActivity() {
        Intent intent = new Intent(this, LlantasActivity.class);
        intent.putExtra(extra_placa, mPlacaIngresada);
        intent.putExtra(extra_evento, mEventoSeleccionado);
        intent.putExtra(extra_evento_int, mEventoSeleccionadoint);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Global.entro_a_encabezado = false;
        Global.entro_a_contenedor = false;
        Global.entro_a_chassis = false;
        Global.entro_a_sellos = false;
        Global.entro_a_llantas = false;
        super.onDestroy();
    }
}

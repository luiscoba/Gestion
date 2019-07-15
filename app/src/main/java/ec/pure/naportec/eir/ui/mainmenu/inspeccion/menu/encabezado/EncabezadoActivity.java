package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.utils.AddItemSpinnerDialogFragment;
import ec.pure.naportec.eir.utils.CustomProgressBar;
import ec.pure.naportec.eir.utils.DateNumberFormatDetector;
import ec.pure.naportec.eir.utils.HighLightArrayAdapterCountMenos1;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.inject.Inject;

public class EncabezadoActivity extends PersonalizadoToolBar {

    private static final String TAG_EncabezadoActivity = "eir." + EncabezadoActivity.class.getSimpleName();
    private String tag = TAG_EncabezadoActivity;

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_evento_int = "extra_evento_int";
    private String mPlacaIngresada, mEventoSeleccionado;
    private int mEventoSeleccionadoInt;

    private HighLightArrayAdapterCountMenos1 mAdapter;
    private ArrayList lstSizeCont;

    private BiMap<String, String> biMap_uuid_descripcion;

    private Spinner spn_size;
    private EditText contenedor, sellos, carga, peso, temperatura, ventilacion, transportista, exportOimport, agenciaNaviera, maxGross, tare, iso, manofacture, chassis, genset, conductor, cedula, hubometro, generador, combustible, booking, buque, viaje, puerto, observacion;
    private TextView txtv_exportOimport, txtv_hubometro, txtv_generador, txtv_combustible;
    private EditText fecha, noEIR, noTransaccion, noEntrada;

    private InspeccionEntity inspeccionEntity;

    private String mEiricbUuid;

    private Context mContext;

    @Inject
    ViewModelFactory viewModelFactory;
    EncabezadoViewModel encabezadoViewModel;
    EncabezadoSyncViewModel encabezadoSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        encabezadoViewModel = ViewModelProviders.of(this, viewModelFactory).get(EncabezadoViewModel.class);
        encabezadoSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(EncabezadoSyncViewModel.class);

        // enviamos false, para que solo traiga la lista de datos de ROOM
        encabezadoViewModel.insertarReferencia(null, "EIR", "CONT_SIZE", "Description", false);

        inspeccionEntity = encabezadoSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid(); // se obtiene EiricbUuid para poder realizar la actualizacion del Entity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encabezado);

        mContext = getApplicationContext();

        Global.entro_a_encabezado = true;

        setViews();

        dataInjection();

        ponerPlacaYevento();

        List<ReferenciaEntity> referenciaEntityList = encabezadoSyncViewModel.obtenerReferencia("EIR", "CONT_SIZE");
        filtrarReferenciaEntityList(referenciaEntityList);

        spn_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spn_size.getItemAtPosition(position).equals(getString(R.string.text_agregar_nuevo_valor))) {
                    AddItemSpinnerDialogFragment dialogAgregar = AddItemSpinnerDialogFragment.newInstance("TAMAÑO CONTENEDOR", "Agregue un nuevo tamaño");

                    dialogAgregar.show(getSupportFragmentManager(), TAG_EncabezadoActivity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // hay que poner este metodo aqui para que se actualize el spinner luego de insertar algun valor
        cargarSpinner();
    }

    private void setViews() {
        fecha = findViewById(R.id.edtx_fecha);
        noEIR = findViewById(R.id.edtx_noEIR);
        noTransaccion = findViewById(R.id.edtx_noTransaccion);
        noEntrada = findViewById(R.id.edtx_noEntrada);

        txtv_exportOimport = findViewById(R.id.txtv_exportOimport);
        txtv_hubometro = findViewById(R.id.txtv_hubometro);
        txtv_generador = findViewById(R.id.txtv_generador);
        txtv_combustible = findViewById(R.id.txtv_combustible);

        contenedor = findViewById(R.id.edt_contenedor);
        sellos = findViewById(R.id.edt_sellos);
        carga = findViewById(R.id.edt_carga);
        peso = findViewById(R.id.edt_peso);
        temperatura = findViewById(R.id.edt_temperatura);
        ventilacion = findViewById(R.id.edt_ventilacion);
        transportista = findViewById(R.id.edt_transportista);
        exportOimport = findViewById(R.id.edt_exportOimport);
        agenciaNaviera = findViewById(R.id.edt_agencia_naviera);
        maxGross = findViewById(R.id.edt_max_gross);
        tare = findViewById(R.id.edt_tare);
        iso = findViewById(R.id.edt_iso);
        manofacture = findViewById(R.id.edt_manofactured);
        chassis = findViewById(R.id.edt_chassis);
        genset = findViewById(R.id.edt_genset);
        conductor = findViewById(R.id.edt_conductor);
        cedula = findViewById(R.id.edt_cedula);
        hubometro = findViewById(R.id.edt_hubometro);
        generador = findViewById(R.id.edt_generador);
        combustible = findViewById(R.id.edt_combustible);
        booking = findViewById(R.id.edt_booking);
        buque = findViewById(R.id.edt_buque);
        viaje = findViewById(R.id.edt_viaje);
        puerto = findViewById(R.id.edt_puerto);
        observacion = findViewById(R.id.edt_observacion);

        spn_size = findViewById(R.id.spn_size);
        // el id del size genrci_uuid_2 es el codigo de la palabra que esta en el spinner
    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento

        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        mEventoSeleccionadoInt = mIntent.getIntExtra(extra_evento_int, 0);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);

        String[] arr_evento_inspeccion = getResources().getStringArray(R.array.evento_inspeccion);

        if (Global.ubicacion_seleccionada.equals("R9")) {
            if (arr_evento_inspeccion[0] == arr_evento_inspeccion[mEventoSeleccionadoInt - 1]) {
                txtv_exportOimport.setText("INGRESO :");
                txtv_hubometro.setText("HUBOMETRO - INGRESO :");
                txtv_generador.setText("GENERADOR - INGRESO :");
                txtv_combustible.setText("COMBUSTIBLE - INGRESO :");
            } else {
                txtv_exportOimport.setText("SALIDA :");
                txtv_hubometro.setText("HUBOMETRO - SALIDA :");
                txtv_generador.setText("GENERADOR - SALIDA :");
                txtv_combustible.setText("COMBUSTIBLE - SALIDA:");
            }
        } else {
            if (arr_evento_inspeccion[0] == arr_evento_inspeccion[mEventoSeleccionadoInt - 1]) {
                txtv_exportOimport.setText("IMPORTACIÓN :");
                txtv_hubometro.setText("HUBOMETRO - IMPORTACIÓN :");
                txtv_generador.setText("GENERADOR - IMPORTACIÓN :");
                txtv_combustible.setText("COMBUSTIBLE - IMPORTACIÓN :");
            } else {
                txtv_exportOimport.setText("EXPORTACIÓN :");
                txtv_hubometro.setText("HUBOMETRO - EXPORTACIÓN :");
                txtv_generador.setText("GENERADOR - EXPORTACIÓN :");
                txtv_combustible.setText("COMBUSTIBLE - EXPORTACIÓN:");
            }
        }
    }

    public void setDatos(String dato) {
        // con esto hacemos la insercion de un SIZE en el spinner
        encabezadoViewModel.insertarReferencia(dato, "EIR", "CONT_SIZE", "Description", true);//true para hacer aplicar el Obserevable, false solo para la base
        encabezadoViewModel.obtenerTodaReferenciaLiveData()
                .observe(this, resource -> {
                    if (resource.isLoading()) {
                        CustomProgressBar.getInstance().showProgressDialog(EncabezadoActivity.this);
                    } else if (!resource.data.isEmpty()) {
                        Toast.makeText(this, dato + " insertado correctamente", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "No se pudo insertar nuevo valor", Toast.LENGTH_LONG).show();
                    CustomProgressBar.getInstance().closeProgressDialog();
                });
    }

    private void cargarSpinner() {

        encabezadoViewModel.obtenerTodaReferenciaLiveData()
                .observe(this, resource -> {
                    if (resource.isLoading()) {
                        CustomProgressBar.getInstance().showProgressDialog(EncabezadoActivity.this);
                    } else if (!resource.data.isEmpty()) {
                        filtrarReferenciaEntityList(resource.data);
                    } else
                        Toast.makeText(this, "No se pudo insertar nuevo valor", Toast.LENGTH_LONG).show();
                    CustomProgressBar.getInstance().closeProgressDialog();
                });
    }

    private void filtrarReferenciaEntityList(List<ReferenciaEntity> referenciaEntityList) {
        // creamos un HashMap para posteriormente obtener el codigo del item seleccionado
        biMap_uuid_descripcion = HashBiMap.create();

        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMap_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        lstSizeCont = new ArrayList<>(biMap_uuid_descripcion.values());

        lstSizeCont.add(getString(R.string.text_agregar_nuevo_valor));
        lstSizeCont.add("Seleccione Size");

        cargar_sizecont(spn_size, lstSizeCont);
    }

    public void cargar_sizecont(Spinner spinnerSizeCont, ArrayList<String> lstSizeCont) {
        mAdapter = new HighLightArrayAdapterCountMenos1(this, R.layout.spinner_itemstyle, lstSizeCont);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);
        spinnerSizeCont.setAdapter(mAdapter);
        // mostramos el lastIndex
        spinnerSizeCont.setSelection(mAdapter.getCount());//index starts from 0. so if spinner has 5 item the lastIndex is 4
        //ponemos esto aqui para que se muestre el dato persistente en el spinner seleccionado
        if (inspeccionEntity.getGenrciUuid2() == null)
            spn_size.setSelection(lstSizeCont.size() - 1);
        else
            spn_size.setSelection(mAdapter.getPosition(biMap_uuid_descripcion.get(inspeccionEntity.getGenrciUuid2())));
    }

    private SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onStart() {
        super.onStart();
        //           20190708 18:33:54
        fecha.setText(inspeccionEntity.getEiricbCreatedAt());
        noEIR.setText(inspeccionEntity.getEiricbCodigo());
        noTransaccion.setText(inspeccionEntity.getEiricbNrotransaccion());
        noEntrada.setText(inspeccionEntity.getEiricbNroentrada());
        //System.out.println("valor que referencia " + biMap_uuid_descripcion.get(eirtInspeccionCabEntityList.get(0).getGenrciUuid2()));
        //usando mAdapter.getPosition() obtenemos la posicion del valor en el adapter, sin recorrer el adapter
        System.out.println("biMap_uuid_descripcion.get(inspeccionEntity.getGenrciUuid2())) " + biMap_uuid_descripcion.get(inspeccionEntity.getGenrciUuid2()));
        contenedor.setText(inspeccionEntity.getEiricbContenedor());
        sellos.setText(inspeccionEntity.getEiricbSellos());
        carga.setText(inspeccionEntity.getEiricbCarga());
        peso.setText(inspeccionEntity.getEiricbPeso());   // controlar que ingrese Double
        temperatura.setText(inspeccionEntity.getEiricbTempera()); // controlar que ingrese Double
        ventilacion.setText(inspeccionEntity.getEiricbVentilac()); // controlar que ingrese Double
        transportista.setText(inspeccionEntity.getEiricbTransporName());
        exportOimport.setText(inspeccionEntity.getEiricbExpimpName());
        agenciaNaviera.setText(inspeccionEntity.getEiricbNaviera());
        maxGross.setText(inspeccionEntity.getEiricbMaxgross()); // controlar que ingrese Double
        tare.setText(inspeccionEntity.getEiricbTare()); // controlar el ingreso sea Double
        iso.setText(inspeccionEntity.getEiricbIso()); // controlar que sea Double
        manofacture.setText(inspeccionEntity.getEiricbManufactured());
        chassis.setText(inspeccionEntity.getEiricbChasis());
        genset.setText(inspeccionEntity.getEiricbGenset());
        conductor.setText(inspeccionEntity.getEiricbChoferName());
        cedula.setText(inspeccionEntity.getEiricbChoferCedu());
        hubometro.setText(inspeccionEntity.getEiricbValorHubo()); //controlar el ingreso sea Double
        generador.setText(inspeccionEntity.getEiricbValorGene()); // controlar el ingreso de Double
        combustible.setText(inspeccionEntity.getEiricbValorComb());
        booking.setText(inspeccionEntity.getEiricbBlbooking());
        buque.setText(inspeccionEntity.getEiricbBuque());
        viaje.setText(inspeccionEntity.getEiricbViaje());
        puerto.setText(inspeccionEntity.getEiricbPuerto());
        observacion.setText(inspeccionEntity.getEiricbObserva1());
    }

    @Override
    protected void onStop() {
        // siempre se setea primero el id del entity para actualizar el entity
        inspeccionEntity.setEiricbUuid(mEiricbUuid);
        // obtenemos los datos que tiene el spinner -> spn_size.getSelectedItem().toString()
        inspeccionEntity.setGenrciUuid2(biMap_uuid_descripcion.inverse().get(spn_size.getSelectedItem().toString()));
        inspeccionEntity.setEiricbContenedor(contenedor.getText().toString());
        inspeccionEntity.setEiricbSellos(sellos.getText().toString());
        inspeccionEntity.setEiricbCarga(carga.getText().toString());
        inspeccionEntity.setEiricbPeso(peso.getText().toString());
        inspeccionEntity.setEiricbTempera(temperatura.getText().toString());
        inspeccionEntity.setEiricbVentilac(ventilacion.getText().toString());
        inspeccionEntity.setEiricbTransporName(transportista.getText().toString());
        inspeccionEntity.setEiricbExpimpName(exportOimport.getText().toString());
        inspeccionEntity.setEiricbNaviera(agenciaNaviera.getText().toString());
        inspeccionEntity.setEiricbMaxgross(maxGross.getText().toString());
        inspeccionEntity.setEiricbTare(tare.getText().toString());
        inspeccionEntity.setEiricbIso(iso.getText().toString());
        inspeccionEntity.setEiricbManufactured(manofacture.getText().toString());
        inspeccionEntity.setEiricbChasis(chassis.getText().toString());
        inspeccionEntity.setEiricbGenset(genset.getText().toString());
        inspeccionEntity.setEiricbChoferName(conductor.getText().toString());
        inspeccionEntity.setEiricbChoferCedu(cedula.getText().toString());
        inspeccionEntity.setEiricbValorHubo(hubometro.getText().toString());
        inspeccionEntity.setEiricbValorGene(generador.getText().toString());
        inspeccionEntity.setEiricbValorComb(combustible.getText().toString());
        inspeccionEntity.setEiricbBlbooking(booking.getText().toString());
        inspeccionEntity.setEiricbBuque(buque.getText().toString());
        inspeccionEntity.setEiricbViaje(viaje.getText().toString());
        inspeccionEntity.setEiricbPuerto(puerto.getText().toString());
        inspeccionEntity.setEiricbObserva1(observacion.getText().toString());

        encabezadoSyncViewModel.actualizarInspeccion(inspeccionEntity);

        super.onStop();
    }

    @Override
    public void onBackPressed() {

        inspeccionEntity.setEiricbBlbooking(booking.getText().toString());
        inspeccionEntity.setEiricbBuque(buque.getText().toString());
        inspeccionEntity.setEiricbViaje(viaje.getText().toString());
        inspeccionEntity.setEiricbPuerto(puerto.getText().toString());
        inspeccionEntity.setEiricbObserva1(observacion.getText().toString());

        // quemamos datos
        inspeccionEntity.setGenrciUuid1("98");
        inspeccionEntity.setEiricbUser("puretech");
        inspeccionEntity.setEiricbPtc("1");

        if (inspeccionEntity.getEiricbBlbooking().equals("") ||
                inspeccionEntity.getEiricbBuque().equals("") ||
                inspeccionEntity.getEiricbViaje().equals("") ||
                inspeccionEntity.getEiricbPuerto().equals("")) {

            Toast.makeText(getApplicationContext(), "Faltan datos obligatorios", Toast.LENGTH_LONG).show();

            booking.requestFocus();

            /*new AlertDialog.Builder(mContext)
                    .setTitle("Faltan datos obligatorios")
                    .setMessage("Desea salir sin grabar?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            booking.requestFocus();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();*/
        } else
            super.onBackPressed();//aqui ya regresa al anterior activity


     /*       PlacaDialogFragment placaDialogFragment = new PlacaDialogFragment();
            Bundle mBundle = new Bundle();
//            mBundle.putString(BUNDLE_ACTIVITY_SELECCIONADO_PlacaDialogFragment, activity_seleccionado);
            placaDialogFragment.setArguments(mBundle);
            placaDialogFragment.show(getSupportFragmentManager(), PlacaDialogFragment.class.getSimpleName());
*/

    }

    // este metodo es para la flecha de atrás
    @Override
    public boolean onSupportNavigateUp() {

        inspeccionEntity.setEiricbBlbooking(booking.getText().toString());
        inspeccionEntity.setEiricbBuque(buque.getText().toString());
        inspeccionEntity.setEiricbViaje(viaje.getText().toString());
        inspeccionEntity.setEiricbPuerto(puerto.getText().toString());
        inspeccionEntity.setEiricbObserva1(observacion.getText().toString());

        // quemamos datos
        inspeccionEntity.setGenrciUuid1("98");
        inspeccionEntity.setEiricbUser("puretech");
        inspeccionEntity.setEiricbPtc("1");

        if (inspeccionEntity.getEiricbBlbooking().equals("") ||
                inspeccionEntity.getEiricbBuque().equals("") ||
                inspeccionEntity.getEiricbViaje().equals("") ||
                inspeccionEntity.getEiricbPuerto().equals("")) {

            Toast.makeText(getApplicationContext(), "Faltan datos obligatorios", Toast.LENGTH_LONG).show();

            booking.requestFocus();

            /*new AlertDialog.Builder(mContext)
                    .setTitle("Faltan datos obligatorios")
                    .setMessage("Desea salir sin grabar?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            booking.requestFocus();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();*/
        } else {
            onBackPressed();//aca debes colocar el metodo que deseas que retorne
        }
        return true;
    }

}

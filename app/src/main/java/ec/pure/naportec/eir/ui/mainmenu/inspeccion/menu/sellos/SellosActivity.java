package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.utils.AddItemSpinnerDialogFragment;
import ec.pure.naportec.eir.utils.CustomProgressBar;
import ec.pure.naportec.eir.utils.HighLightArrayAdapterCountMenos1;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.lifecycle.ViewModelProviders;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SellosActivity extends PersonalizadoToolBar {

    private static final String TAG_SellosActivity = "eir." + SellosActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    private HighLightArrayAdapterCountMenos1 mAdapter;
    private ArrayList<String> lstBaterias;

    private BiMap<String, String> biMap_uuid_descripcion;

    private String mEiricbUuid;

    private Spinner spn_bateria;
    private ToggleButton tglDea, tglDef, tglIea, tglIef;
    private EditText edtTanque1, edtTanque2;

    private InspeccionEntity inspeccionEntity;

    @Inject
    ViewModelFactory viewModelFactory;
    SellosViewModel sellosViewModel;
    SellosSyncViewModel sellosSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        sellosViewModel = ViewModelProviders.of(this, viewModelFactory).get(SellosViewModel.class);
        sellosSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(SellosSyncViewModel.class);

        // enviamos false, para que solo traiga la lista de datos de ROOM
        sellosViewModel.insertarReferencia(null, "EIR", "BATERIA", "Description", false);

        inspeccionEntity = sellosSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellos);

        setViews();

        dataInjection();

        Global.entro_a_sellos = true;



        ponerPlacaYevento();

        List<ReferenciaEntity> referenciaEntityList = sellosSyncViewModel.obtenerReferencia("EIR", "BATERIA");
        filtrarReferenciaEntityList(referenciaEntityList);

        spn_bateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spn_bateria.getItemAtPosition(position).equals(getString(R.string.text_agregar_nuevo_valor))) {
                    AddItemSpinnerDialogFragment dialogAgregar = AddItemSpinnerDialogFragment.newInstance("BATERÍA", "Agregue una nueva batería");

                    dialogAgregar.show(getSupportFragmentManager(), TAG_SellosActivity);
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
        tglDea = findViewById(R.id.tgl_dea);
        tglDef = findViewById(R.id.tgl_def);
        tglIea = findViewById(R.id.tgl_iea);
        tglIef = findViewById(R.id.tgl_ief);
        edtTanque1 = findViewById(R.id.edt_tanque1);
        edtTanque2 = findViewById(R.id.edt_tanque2);
        spn_bateria = findViewById(R.id.spn_bateria);
    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);
    }

    public void setDatos(String dato) {
        sellosViewModel.insertarReferencia(dato, "EIR", "BATERIA", "Description", true);//true para hacer aplicar el Obserevable, false solo para la base
        sellosViewModel.obtenerTodaReferenciaLiveData()
                .observe(this, resource -> {
                    if (resource.isLoading()) {
                        CustomProgressBar.getInstance().showProgressDialog(SellosActivity.this);
                    } else if (!resource.data.isEmpty()) {
                        Toast.makeText(this, dato + " insertado correctamente", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "No se pudo insertar nuevo valor", Toast.LENGTH_LONG).show();
                    CustomProgressBar.getInstance().closeProgressDialog();
                });
    }

    private void cargarSpinner() {

        sellosViewModel.obtenerTodaReferenciaLiveData()
                .observe(this, resource -> {
                    if (resource.isLoading()) {
                        CustomProgressBar.getInstance().showProgressDialog(SellosActivity.this);
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
        lstBaterias = new ArrayList<>(biMap_uuid_descripcion.values());

        lstBaterias.add(getString(R.string.text_agregar_nuevo_valor));
        lstBaterias.add("Seleccione batería");

        cargar_bateria(spn_bateria, lstBaterias);
    }

    public void cargar_bateria(Spinner spinnerBateria, ArrayList<String> listBaterias) {
        mAdapter = new HighLightArrayAdapterCountMenos1(this, R.layout.spinner_itemstyle, listBaterias);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);
        spinnerBateria.setAdapter(mAdapter);
        // mostramos el lastIndex
        spinnerBateria.setSelection(mAdapter.getCount());//index starts from 0. so if spinner has 5 item the lastIndex is 4
        //ponemos esto aqui para que se muestre el dato persistente en el spinner seleccionado
        if (inspeccionEntity.getGenrciUuid3() == null)
            spn_bateria.setSelection(lstBaterias.size() - 1);
        else
            spn_bateria.setSelection(mAdapter.getPosition(biMap_uuid_descripcion.get(inspeccionEntity.getGenrciUuid3())));//getGenrciUuid3 es el id de la bateria
    }

    @Override
    protected void onStart() {
        super.onStart();

        tglDea.setChecked(inspeccionEntity.getEiricbLltSelloDea());
        tglDef.setChecked(inspeccionEntity.getEiricbLltSelloDef());
        tglIea.setChecked(inspeccionEntity.getEiricbLltSelloIea());
        tglIef.setChecked(inspeccionEntity.getEiricbLltSelloIef());
        edtTanque1.setText(inspeccionEntity.getEiricbLltSelloTanque1());
        edtTanque2.setText(inspeccionEntity.getEiricbLltSelloTanque2());
    }

    @Override
    protected void onStop() {

        inspeccionEntity.setEiricbUuid(mEiricbUuid);
        inspeccionEntity.setEiricbLltSelloDea(tglDea.isChecked());
        inspeccionEntity.setEiricbLltSelloDef(tglDef.isChecked());
        inspeccionEntity.setEiricbLltSelloIea(tglIea.isChecked());
        inspeccionEntity.setEiricbLltSelloIef(tglIef.isChecked());
        inspeccionEntity.setEiricbLltSelloTanque1(edtTanque1.getText().toString());
        inspeccionEntity.setEiricbLltSelloTanque2(edtTanque2.getText().toString());
        // obtenemos los datos que tiene el spinner -> spn_size.getSelectedItem().toString()
        inspeccionEntity.setGenrciUuid3(biMap_uuid_descripcion.inverse().get(spn_bateria.getSelectedItem().toString()));

        sellosSyncViewModel.actualizarInspeccion(inspeccionEntity);

        super.onStop();
    }
}

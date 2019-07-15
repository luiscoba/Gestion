package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.LlantaInspeccion;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.viewmodel.DatabaseViewModel;
import ec.pure.naportec.eir.utils.CustomProgressBar;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoActivity;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LlantasActivity extends PersonalizadoToolBar {

    private static final String TAG_LlantasActivity = "eir." + EncabezadoActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelFactory viewModelFactory;
    LlantasViewModel llantasViewModel;
    DatabaseViewModel databaseViewModel;
    LlantasSyncViewModel llantasSyncViewModel;

    ArrayList<String> mLlantasPosic = new ArrayList<>();
    ArrayList<String> mLlantasMarca = new ArrayList<>();
    ArrayList<String> mLlantasEstado = new ArrayList<>();

    private ListView comboList;
    private BiMap<String, String> biMapMarca_uuid_descripcion, biMapEstado_uuid_descripcion, biMapPosicion_uuid_descripcion;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    private void dataInjection() {
        AndroidInjection.inject(this);
        databaseViewModel = ViewModelProviders.of(this, viewModelFactory).get(DatabaseViewModel.class);
        llantasViewModel = ViewModelProviders.of(this, viewModelFactory).get(LlantasViewModel.class);
        llantasSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(LlantasSyncViewModel.class);

        inspeccionEntity = llantasSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion

        // enviamos false, para que solo traiga la lista de datos de ROOM
        llantasViewModel.insertarReferencia(null, "EIR", "LLANTA_MARCA", "DatoNum1", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llantas);
        comboList = findViewById(R.id.combosListView);

        dataInjection();

        Global.entro_a_llantas = true;

        List<ReferenciaEntity> lstPosicionReferencia = llantasSyncViewModel.obtenerReferencia("EIR", "LLANTA_POSIC");

        cargarListaPosic(lstPosicionReferencia);

        ponerPlacaYevento();

        Button btnMarca = findViewById(R.id.btn_marca);
        btnMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View listItem0 = comboList.getChildAt(0);
                Spinner spnMarca0 = (Spinner) listItem0.findViewById(R.id.spn_marca);

                for (int i = 0; i < comboList.getChildCount(); i++) {
                    // Get row's spinner
                    View listItem = comboList.getChildAt(i);
                    Spinner spnMarca = (Spinner) listItem.findViewById(R.id.spn_marca);

                    spnMarca.setSelection(spnMarca0.getSelectedItemPosition());
                }
            }
        });

        btnMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View listItem0 = comboList.getChildAt(0);
                Spinner spnEstado0 = (Spinner) listItem0.findViewById(R.id.spn_marca);

                for (int i = 0; i < comboList.getChildCount(); i++) {
                    // Get row's spinner
                    View listItem = comboList.getChildAt(i);
                    Spinner spnMarca = (Spinner) listItem.findViewById(R.id.spn_marca);

                    spnMarca.setSelection(spnEstado0.getSelectedItemPosition());
                }
            }
        });

        Button btnEstado = findViewById(R.id.btn_estado);
        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View listItem0 = comboList.getChildAt(0);
                Spinner spnEstado0 = (Spinner) listItem0.findViewById(R.id.spn_estado);

                for (int i = 0; i < comboList.getChildCount(); i++) {
                    // Get row's spinner
                    View listItem = comboList.getChildAt(i);
                    Spinner spnEstado = (Spinner) listItem.findViewById(R.id.spn_estado);

                    spnEstado.setSelection(spnEstado0.getSelectedItemPosition());
                }
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

    public void setDatos(String dato) {

//        System.out.println("Llega el dato LlantasActivity " + dato);
        llantasViewModel.insertarReferencia(dato, "EIR", "LLANTA_MARCA", "DatoNum1", true);//true para hacer aplicar el Obserevable, false solo para la base
        llantasViewModel.obtenerTodaReferenciaLiveData()
                .observe(this, resource -> {
                    if (resource.isLoading()) {
                        CustomProgressBar.getInstance().showProgressDialog(LlantasActivity.this);
                    } else if (!resource.data.isEmpty()) {
                        Toast.makeText(this, dato + " insertado correctamente", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "No se pudo insertar nuevo valor", Toast.LENGTH_LONG).show();
                    CustomProgressBar.getInstance().closeProgressDialog();
                });
    }

    private void cargarListaPosic(List<ReferenciaEntity> referenciaEntityList) {
        biMapPosicion_uuid_descripcion = HashBiMap.create();
        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMapPosicion_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mLlantasPosic = new ArrayList<>(biMapPosicion_uuid_descripcion.values());

        List<ReferenciaEntity> lstMarcaReferencia = llantasSyncViewModel.obtenerReferencia("EIR", "LLANTA_MARCA");
        cargarListaMarca(lstMarcaReferencia);
    }

    private void cargarListaMarca(List<ReferenciaEntity> referenciaEntityList) {
        biMapMarca_uuid_descripcion = HashBiMap.create();

        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMapMarca_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mLlantasMarca = new ArrayList<>(biMapMarca_uuid_descripcion.values());

        mLlantasMarca.add(getString(R.string.text_agregar_nuevo_valor));
        mLlantasMarca.add(getString(R.string.text_seleccione_un_valor));

        List<ReferenciaEntity> lstEstadoReferencia = llantasSyncViewModel.obtenerReferencia("EIR", "LLANTA_ESTADO");
        cargarListaEstado(lstEstadoReferencia);
    }

    private void cargarListaEstado(List<ReferenciaEntity> referenciaEntityList) {

        biMapEstado_uuid_descripcion = HashBiMap.create();

        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMapEstado_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mLlantasEstado = new ArrayList<>(biMapEstado_uuid_descripcion.values());
        mLlantasEstado.add(getString(R.string.text_seleccione_un_valor));

        List<LlantaInspeccion> lstLlantas = inspeccionEntity.getLlanta();

        LlantasAdapter llantasAdapter = new LlantasAdapter(this, R.layout.item_llanta, mLlantasPosic, mLlantasMarca, biMapMarca_uuid_descripcion, mLlantasEstado, biMapEstado_uuid_descripcion, lstLlantas, getSupportFragmentManager());
        comboList.setAdapter(llantasAdapter);
        // esta funcion se encarga de alargar la altura del ListView dentro del Activity
        setListViewHeightBasedOnItems(comboList);
    }

    @Override
    protected void onStop() {

        inspeccionEntity.setEiricbUuid(mEiricbUuid);

        List<String> selectionsPosicion = new ArrayList<String>();
        List<String> selectionsMarca = new ArrayList<String>();
        List<String> selectionsEstado = new ArrayList<String>();

        for (int i = 0; i < comboList.getChildCount(); i++) {

            // Get row's spinner
            View listItem = comboList.getChildAt(i);
            TextView textView = (TextView) listItem.findViewById(R.id.textViewPosicion);
            Spinner spnMarca = (Spinner) listItem.findViewById(R.id.spn_marca);
            Spinner spnEstado = (Spinner) listItem.findViewById(R.id.spn_estado);

            // Get selection
            String selectionMarca = (String) spnMarca.getSelectedItem();
            selectionsMarca.add(selectionMarca);

            String selectionEstado = (String) spnEstado.getSelectedItem();
            selectionsEstado.add(selectionEstado);

            String selectionPosicion = (String) textView.getText().toString();
            selectionsPosicion.add(selectionPosicion);
        }

        List<LlantaInspeccion> llantaInspeccions = new ArrayList<>();
        for (int i = 0; i < selectionsMarca.size(); i++) {
            LlantaInspeccion llanta = new LlantaInspeccion();

            System.out.println("biMap fila " + i + " -> " + selectionsMarca.get(i));

            llanta.setGenrciUuid1(biMapPosicion_uuid_descripcion.inverse().get(selectionsPosicion.get(i)));
            llanta.setGenrciUuid2(biMapMarca_uuid_descripcion.inverse().get(selectionsMarca.get(i)));
            llanta.setGenrciUuid3(biMapEstado_uuid_descripcion.inverse().get(selectionsEstado.get(i)));

            llantaInspeccions.add(llanta);
        }
        System.out.println();

        for (int i = 0; i < selectionsMarca.size(); i++) {
            System.out.println("marca fila " + i + " -> " + selectionsMarca.get(i));
        }

        for (int i = 0; i < selectionsEstado.size(); i++) {
            System.out.println("estado fila " + i + " -> " + selectionsEstado.get(i));
        }

        inspeccionEntity.setLlanta(llantaInspeccions);
        llantasSyncViewModel.actualizarInspeccion(inspeccionEntity);

        super.onStop();
    }

    // este metodo sirve para que el listView se expanda en el activity
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }

}

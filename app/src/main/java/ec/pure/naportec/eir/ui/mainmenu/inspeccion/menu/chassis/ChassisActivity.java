package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.chassis;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoActivity;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ChassisActivity extends PersonalizadoToolBar {

    private static final String TAG_ChassisActivity = "eir." + EncabezadoActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    private BiMap<String, String> biMap_uuid_descripcion_Referencia;
    private Map<String, Boolean> map_uuid_estado;

    ArrayList<String> mLstDescripciones = new ArrayList<>();
    ArrayList<String> mLstParentesis = new ArrayList<>();
    ArrayList<String> mLstUuid;

    private ListView comboList;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    @Inject
    ViewModelFactory viewModelFactory;

    ChassisSyncViewModel chassisSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);

        chassisSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(ChassisSyncViewModel.class);

        inspeccionEntity = chassisSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chassis);

        dataInjection();

        Global.entro_a_chassis = true;

        comboList = findViewById(R.id.combosListView);

        ponerPlacaYevento();

        buscarListaChassis("EIR", "CHASIS");
    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);
    }

    private void buscarListaChassis(String genmodCodigo, String gentrfCodigo) {

        chassisSyncViewModel.obtenerReferencias(genmodCodigo, gentrfCodigo)
                .observe(this, new Observer<List<ReferenciaEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ReferenciaEntity> referenciaEntityList) {
                        cargarListaCheck(referenciaEntityList);
                    }
                });
    }

    private void cargarListaCheck(List<ReferenciaEntity> referenciaEntityList) {
        // creamos un HashMap para posteriormente obtener el codigo del item seleccionado
        biMap_uuid_descripcion_Referencia = HashBiMap.create();
        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMap_uuid_descripcion_Referencia.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }// guardamos las descripciones en la lista
        mLstUuid = new ArrayList<>(biMap_uuid_descripcion_Referencia.inverse().values());
        mLstDescripciones = new ArrayList<>(biMap_uuid_descripcion_Referencia.values());

        // cargamos un map con el estado del chassis
        map_uuid_estado = new HashMap<>();
        if (inspeccionEntity.getChasis() != null)
            for (ChassisInspeccion chassisInsp : inspeccionEntity.getChasis()) {
                map_uuid_estado.put(chassisInsp.getGenrciUuid(), chassisInsp.getEiridhEstado());
            }

        buscarParentesis("EIR", "CHASIS");
    }

    private void buscarParentesis(String genmodCodigo, String gentrfCodigo) {

        chassisSyncViewModel.obtenerReferencias(genmodCodigo, gentrfCodigo)
                .observe(this, new Observer<List<ReferenciaEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ReferenciaEntity> referenciaEntityList) {
                        cargarListaDatoVar1(referenciaEntityList);
                    }
                });
    }

    private void cargarListaDatoVar1(List<ReferenciaEntity> referenciaEntityList) {

        ArrayList<String> lista = new ArrayList<>();
        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            lista.add(referenciaEntity.getGenrciDatovar1());
        }

        mLstParentesis.addAll(lista);

        ChassisAdapter myAdapter = new ChassisAdapter(this, R.layout.item_chassis, biMap_uuid_descripcion_Referencia, mLstDescripciones, mLstUuid, mLstParentesis, map_uuid_estado);
        comboList.setAdapter(myAdapter);
        // esta funcion se encarga de alargar la altura del ListView dentro del Activity
        setListViewHeightBasedOnItems(comboList);
    }

    @Override
    protected void onStop() {

        inspeccionEntity.setEiricbUuid(mEiricbUuid);

        List<ChassisInspeccion> chassisInspeccions = new ArrayList<>();
        for (int i = 0; i < comboList.getChildCount(); i++) {
            ChassisInspeccion chassis = new ChassisInspeccion();
            // obtenemos el CheckBox
            View listItem = comboList.getChildAt(i);
            CheckBox chkbChassis = listItem.findViewById(R.id.chkb_chassis);

            System.out.println("text " + chkbChassis.getText().toString() + " isChecked() " + chkbChassis.isChecked());

            chassis.setGenrciUuid(biMap_uuid_descripcion_Referencia.inverse().get(mLstDescripciones.get(i)));
            chassis.setEiridhEstado(chkbChassis.isChecked());

            chassisInspeccions.add(chassis);
        }

        for (ChassisInspeccion chassis : chassisInspeccions) {
            System.out.println("chassis.getGenrciUuid() " + chassis.getGenrciUuid() + " chassis.getEiridhEstado() " + chassis.getEiridhEstado());
        }

        inspeccionEntity.setChasis(chassisInspeccions);
        chassisSyncViewModel.actualizarInspeccion(inspeccionEntity);

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

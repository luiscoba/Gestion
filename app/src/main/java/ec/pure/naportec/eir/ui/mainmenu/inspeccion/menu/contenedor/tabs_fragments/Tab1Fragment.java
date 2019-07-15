package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;
import ec.pure.naportec.eir.data.remote.model.Daniado;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosDialogFragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.grid_imagen.ImagenAdapter;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosDialogFragment.OnMaltratosDialogFragmentListener;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosSyncViewModel;


public class Tab1Fragment extends Fragment implements OnMaltratosDialogFragmentListener, HasSupportFragmentInjector {

    private static final String TAG_Tab1Fragment = "eir." + Tab1Fragment.class.getSimpleName();

    private View vwOpcion;

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_grid_seleccionado = "extra_grid_seleccionado";
    private String mPlacaIngresada, mEventoSeleccionado;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;


    private List<String> mContenedorDaños = new ArrayList<>();
    private List<ReferenciaEntity> mLstDaños;
    private BiMap<String, String> biMapDaño_uuid_descripcion;
    private GridView grid;

    private List<ContenedorInspeccion> lstContenedores = new ArrayList<>();
    private  List<ContenedorInspeccion> lstContenedoresTab1 = new ArrayList<>();
    private BiMap<String, String> biMapContenedor_uuid_descripcion;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private static final int NUMERO_DE_COLUMNAS = 3;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelFactory viewModelFactory;
    DañosSyncViewModel dañosSyncViewModel;

    private void dataInjection() {
        AndroidSupportInjection.inject(this);
        dañosSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(DañosSyncViewModel.class);

        inspeccionEntity = dañosSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataInjection();

        // ================================== Descripcion de da;os
        // creamos un HashMap para posteriormente obtener el codigo del item seleccionado
        mLstDaños = dañosSyncViewModel.obtenerReferencia("EIR", "CONT_DANO");
        biMapDaño_uuid_descripcion = HashBiMap.create();
        for (ReferenciaEntity referenciaEntity : mLstDaños) {
            biMapDaño_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mContenedorDaños = new ArrayList<>(biMapDaño_uuid_descripcion.values());
        // ================================== Descripcion de da;os

        if (inspeccionEntity.getContenedor() != null){
            for (ContenedorInspeccion contenedor : inspeccionEntity.getContenedor()) {
                if (contenedor.getEiridcCara().equals("1")) { // porque es la cara 1
                    lstContenedores.add(contenedor);
                }
            }
/*
            for (int i = 0; i <= Math.pow(2, NUMERO_DE_COLUMNAS); i++) {
                if (lstContenedores.get(i).getEiridcPosic().equals(String.valueOf(i))) {
                    ContenedorInspeccion contenedor = new ContenedorInspeccion();
                    contenedor.setEiridcPosic(String.valueOf(i));
                    lstContenedoresTab1.add(contenedor);
                } else {
                    lstContenedoresTab1.add(lstContenedores.get(i));
                }
            }
*/
            for (ContenedorInspeccion contenedor : lstContenedoresTab1) {
                System.out.println("contenedor.getEiridcPosic() " + contenedor.getEiridcPosic());
            }
        }

        Bundle bundle = getArguments();
        mPlacaIngresada = bundle.getString(extra_placa);
        mEventoSeleccionado = bundle.getString(extra_evento);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

/*
        biMapPosicion_uuid_descripcion = HashBiMap.create();
        for (ReferenciaEntity referenciaEntity : referenciaEntityList) {
            biMapPosicion_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mLlantasPosic = new ArrayList<>(biMapPosicion_uuid_descripcion.values());
*/


        Resources res = getResources();
        // con este TypedArray obtenemos el el array de enteros que esta en el xml
        TypedArray array_de_imagenes = res.obtainTypedArray(R.array.array_tab1_nombre_de_imagenes_contenedor);

        grid = view.findViewById(R.id.grid_imagen);
        grid.setNumColumns(NUMERO_DE_COLUMNAS);
        ImagenAdapter adaptador = new ImagenAdapter(getContext(), array_de_imagenes, lstContenedoresTab1);

        grid.setAdapter(adaptador);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DañosDialogFragment opciones = new DañosDialogFragment();
//               DañosDialogFragment opciones = DañosDialogFragment.newInstance("", "");
                // con esta linea se hace el envio de desde el dialog a el fragment <--<--
                opciones.setTargetFragment(Tab1Fragment.this, 0);
                Bundle mBundle = new Bundle();
                mBundle.putString(extra_placa, mPlacaIngresada);
                mBundle.putString(extra_evento, mEventoSeleccionado);
                mBundle.putInt(extra_grid_seleccionado, position);
                opciones.setArguments(mBundle);
                opciones.show(getFragmentManager(), TAG_Tab1Fragment);// mostramos DañosDialogFragment
                vwOpcion = view;
            }
        });

        return view;
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        for (int i = 0; i < grid.getChildCount(); i++) {
//            TextView child = (TextView) grid.getChildAt(i);
//            // do stuff with child view
//            child.setText("pos " + i);
//            child.setVisibility(View.VISIBLE);
//            System.out.println("se imprime " + i);
//        }
//
//    }

    @Override
    public void obtenerOpciones(List<Daniado> lstOpcionSeleccionada) {
        String concatena_opciones = "";
        TextView textoActual = vwOpcion.findViewById(R.id.txt_imagen);
        textoActual.setTextColor(Color.RED);
        textoActual.setVisibility(View.VISIBLE);
        for (Daniado opcion : lstOpcionSeleccionada) {
            concatena_opciones = concatena_opciones + opcion.getGenrciUuid() + " ";
        }
        textoActual.setText(concatena_opciones);
    }

}
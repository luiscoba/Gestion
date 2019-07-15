package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ec.pure.naportec.eir.R;

import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;
import ec.pure.naportec.eir.data.remote.model.Daniado;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosDialogFragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosDialogFragment.OnMaltratosDialogFragmentListener;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.grid_imagen.ImagenAdapter;

public class Tab2Fragment extends Fragment implements OnMaltratosDialogFragmentListener {

    private static final String TAG_Tab2Fragment = "eir." + Tab2Fragment.class.getSimpleName();

    private View vwOpcion;

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    private ContenedorInspeccion contenedorInspeccion;
    private ContenedorInspeccion mContenedor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mPlacaIngresada = bundle.getString(extra_placa);
        mEventoSeleccionado = bundle.getString(extra_evento);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        Resources res = getResources();
        // con este TypedArray obtenemos el el array de enteros que esta en el xml
        TypedArray array_de_imagenes = res.obtainTypedArray(R.array.array_tab2_nombre_de_imagenes_contenedor);

        GridView grid = view.findViewById(R.id.grid_imagen);
        grid.setNumColumns(4);
        ImagenAdapter adaptador = new ImagenAdapter(getContext(), array_de_imagenes, new ArrayList<ContenedorInspeccion>());

        grid.setAdapter(adaptador);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DañosDialogFragment opciones = new DañosDialogFragment();
                // con esta linea se hace el envio de desde el dialog a el fragment <--<--
                opciones.setTargetFragment(Tab2Fragment.this, 0);
                Bundle mBundle = new Bundle();
                mBundle.putString(extra_placa, mPlacaIngresada);
                mBundle.putString(extra_evento, mEventoSeleccionado);
                opciones.setArguments(mBundle);
                opciones.show(getFragmentManager(), TAG_Tab2Fragment);// mostramos DañosDialogFragment

                vwOpcion = view;
            }
        });
        return view;
    }

    /*    @Override
        public void obtenerOpciones(String opcionSeleccionada) {

            TextView textoActual = vwOpcion.findViewById(R.id.txt_imagen);

            textoActual.setTextColor(Color.RED);
            textoActual.setVisibility(View.VISIBLE);
            textoActual.setText(opcionSeleccionada);
        }*/
    @Override
    public void obtenerOpciones( List<Daniado> lstOpcionSeleccionada) {

    }
}
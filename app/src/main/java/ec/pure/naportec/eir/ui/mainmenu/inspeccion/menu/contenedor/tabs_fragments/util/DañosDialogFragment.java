package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;

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

public class DañosDialogFragment extends DialogFragment implements HasSupportFragmentInjector {

    private static final String TAG_DañosDialogFragment = "eir." + DañosDialogFragment.class.getSimpleName();

    private static List<String> slstOpcionSeleccionada;
    private String opcion_seleccionado_del_arreglo;

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_grid_seleccionado = "extra_grid_seleccionado";

    private String mPlacaIngresada, mEventoSeleccionado;
    private int mGridSeleccionado;

    private List<String> mContenedorDaños = new ArrayList<>();
    private List<String> mStrDañosSeleccionados = new ArrayList<>();
    private List<ReferenciaEntity> mLstDaños;
    private BiMap<String, String> biMapDaño_uuid_descripcion;


    private InspeccionEntity inspeccionEntity;
    private ContenedorInspeccion mContenedor;
    private String mEiricbUuid;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelFactory viewModelFactory;
    DañosSyncViewModel dañosSyncViewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void dataInjection() {
        AndroidSupportInjection.inject(this);
        dañosSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(DañosSyncViewModel.class);

        inspeccionEntity = dañosSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public interface OnMaltratosDialogFragmentListener {
        void obtenerOpciones(List<Daniado> lstOpcionSeleccionada);
    }

    OnMaltratosDialogFragmentListener mCallback = new OnMaltratosDialogFragmentListener() {
        @Override
        public void obtenerOpciones(List<Daniado> lstOpcionSeleccionada) {
            System.out.println("MaltratoDialogFragment lstOpcionSeleccionada " + lstOpcionSeleccionada);
        }
    }; //comente ahoria a las 1244 por probar la guardada de da;os

    public static DañosDialogFragment newInstance(String title, String message) {
        DañosDialogFragment f = new DañosDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataInjection();

        Bundle mArgs = getArguments();
        mPlacaIngresada = mArgs.getString(extra_placa);
        mEventoSeleccionado = mArgs.getString(extra_evento);

        mGridSeleccionado = mArgs.getInt(extra_grid_seleccionado);

        if (inspeccionEntity.getContenedor() != null) {
            for (ContenedorInspeccion contenedor : inspeccionEntity.getContenedor()) {
                if (contenedor.getEiridcCara().equals("1") && contenedor.getEiridcPosic().equals(String.valueOf(mGridSeleccionado))) {//porque es la cara 1
                    for (Daniado daño : contenedor.getDaniado()) {
                        mStrDañosSeleccionados.add(daño.getGenrciUuid());
                    }
                }
            }
        }

        if (mStrDañosSeleccionados.size() >= 0)
            for (String strdanio : mStrDañosSeleccionados) {
                System.out.println("strdanio " + strdanio);
            }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mLstDaños = dañosSyncViewModel.obtenerReferencia("EIR", "CONT_DANO");
        biMapDaño_uuid_descripcion = HashBiMap.create();
        for (ReferenciaEntity referenciaEntity : mLstDaños) {
            biMapDaño_uuid_descripcion.put(referenciaEntity.getGenrciUuid(), referenciaEntity.getGenrciDescripcion());
        }
        mContenedorDaños = new ArrayList<>(biMapDaño_uuid_descripcion.values());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_dialogFragment_maltratos);

        // --> se crea los arreglos que tienen las palabras y acronimos de Maltratos
        final String[] Maltratos = new String[mLstDaños.size()];
        final String[] MaltratoAcronimo = new String[mLstDaños.size()];

        for (int i = 0; i < mLstDaños.size(); i++) {
            Maltratos[i] = mLstDaños.get(i).getGenrciDescripcion();
            MaltratoAcronimo[i] = mLstDaños.get(i).getGenrciCodigo();
        }
        // <-- fin de la creacion de los arreglos

        dialogoLista(builder, Maltratos);

/*
        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // borrar si es que esto ya no se necesita, esto envia los datos al activity ContenedorActivity
                mCallback.obtenerOpciones(MaltratoAcronimo[sOpcionSeleccionada]);
                opcion_seleccionado_del_arreglo = MaltratoAcronimo[sOpcionSeleccionada];
                // con esta linea se hace el envio de desde el dialog a el fragment -->-->
                ((OnMaltratosDialogFragmentListener) getTargetFragment()).obtenerOpciones(slstOpcionSeleccionada);
            }
        });
*/
/* lo ocultamos por el momento
        builder.setNeutralButton(R.string.dialogo_btn_tomar_foto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                AppUtils.verificaSD_Directory("/eir_imagenes/");
                if (!AppUtils.bool_sdAccesoEscritura || !AppUtils.bool_sdDisponible)
                    Toast.makeText(getActivity(),
                            "Problemas con la Memoria Externa", Toast.LENGTH_SHORT).show();
                if (!AppUtils.bool_dirExist)
                    Toast.makeText(getActivity(),
                            "No se puede crear el Directorio de imagenes", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), VariasImageCameraActivity.class);
                i.putExtra(VariasImageCameraActivity.ARG_PANTALLA_ORIGEN, VariasImageCameraActivity.PANTALLA_UNO);
                i.putExtra(VariasImageCameraActivity.ARG_PHOTO_NAME_BASE, "XYZ123"+"_");
                i.putExtra(VariasImageCameraActivity.EXTRA_ENTIDAD_CODE, "XYZ123");
                i.putExtra(VariasImageCameraActivity.EXTRA_TITULO, "Contenedor");
                i.putExtra(VariasImageCameraActivity.EXTRA_CONSULTA, false);

                i.putExtra(extra_placa, mPlacaIngresada);
                i.putExtra(extra_evento, mEventoSeleccionado);
                startActivityForResult(i, VariasImageCameraActivity.PANTALLA_UNO);

            }
        });
*/
        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }
/*
    private void dialogoLista(AlertDialog.Builder builder, final String[] MaltratoAcronimo) {
        builder.setSingleChoiceItems(MaltratoAcronimo, sOpcionSeleccionada, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sOpcionSeleccionada = which;
            }
        });
    }
    */

    private void dialogoLista(AlertDialog.Builder builder, final String[] colores) {
        boolean[] multi;
        multi = new boolean[colores.length];

        for (int i = 0; i < colores.length; i++) {
            if (mStrDañosSeleccionados.contains(biMapDaño_uuid_descripcion.inverse().get(colores[i])))
                multi[i] = true;
            else
                multi[i] = false;
        }

        builder.setTitle("aqui va titulo");
        builder.setMultiChoiceItems(colores, multi, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });

        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // guardamos los da;os seleccionados
                saveItems(multi, colores);
            }
        });

        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
    }

    private void saveItems(boolean[] multi, final String[] colores) {

        List<Daniado> lstDaniados = new ArrayList<>();
        for (int i = 0; i < multi.length; i++) {
            Daniado daniado = new Daniado();
            if (multi[i]) {
                daniado.setGenrciUuid(biMapDaño_uuid_descripcion.inverse().get(colores[i]));
                lstDaniados.add(daniado);
            }
        }
        // se envian los datos a
        ((OnMaltratosDialogFragmentListener) getTargetFragment()).obtenerOpciones(lstDaniados);

        inspeccionEntity.setEiricbUuid(mEiricbUuid);
        List<ContenedorInspeccion> lstContendedoresExistentes = inspeccionEntity.getContenedor();

        if (inspeccionEntity.getContenedor() == null) {
            lstContendedoresExistentes = new ArrayList<>();
        } else {
            for (int i = 0; i < lstContendedoresExistentes.size(); i++) {
                if (lstContendedoresExistentes.get(i).getEiridcCara().equals("1") && lstContendedoresExistentes.get(i).getEiridcPosic().equals(String.valueOf(mGridSeleccionado))) {//porque es la cara 1
                    // quitamos el contenedor con los datos actuales
                    lstContendedoresExistentes.remove(i);
                }
            }
        }
        // agregamos un nuevo contendor con datos actualizados
        ContenedorInspeccion contenedor = new ContenedorInspeccion();
        contenedor.setEiridcCara("1");
        contenedor.setEiridcPosic(String.valueOf(mGridSeleccionado));
        contenedor.setDaniado(lstDaniados);

        lstContendedoresExistentes.add(contenedor);

        inspeccionEntity.setContenedor(lstContendedoresExistentes);
        dañosSyncViewModel.actualizarInspeccion(inspeccionEntity);

    }
}


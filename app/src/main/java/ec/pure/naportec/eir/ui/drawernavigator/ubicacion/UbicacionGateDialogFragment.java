package ec.pure.naportec.eir.ui.drawernavigator.ubicacion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;

import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.utils.HighLightArrayAdapter;

public class UbicacionGateDialogFragment extends DialogFragment {

    private ArrayList<String> mListUbicacion = new ArrayList<>();

    private ArrayList<String> mListGate = new ArrayList<>();

    private ArrayList<String> mListGateBananaPuerto = new ArrayList<>();
    private ArrayList<String> mListGateR9 = new ArrayList<>();
    private ArrayList<String> mListGateCheckPoint = new ArrayList<>();

    private HighLightArrayAdapter mAdapterUbicacion, mAdapterGate;

    private Spinner spn_ubicacion;
    private Spinner spn_gate;

    private TextView gate, nombre_de_usuario;

    OnColocarValoresUbicacionDlgFrgListener onColocarValoresUbicacionDlgFrgListener;

    public interface OnColocarValoresUbicacionDlgFrgListener {
        void ponerValores(String valor1, String valor2);
    }

    public void setOnColocarValoresUbicacionDlgFrgListener(OnColocarValoresUbicacionDlgFrgListener listener) {
        onColocarValoresUbicacionDlgFrgListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gate = getActivity().findViewById(R.id.txv_gate);
        nombre_de_usuario = getActivity().findViewById(R.id.txv_nombre_de_usuario);

        mListUbicacion.add("Banana Puerto");
        mListUbicacion.add("R9");
        mListUbicacion.add("Check Point");

        mListGateBananaPuerto.add("gate1 Ban");
        mListGateBananaPuerto.add("gate2 Ban");
        mListGateBananaPuerto.add("gate3 Ban");
        mListGateBananaPuerto.add("gate4 Ban");

        mListGateR9.add("gate1 R9");
        mListGateR9.add("gate2 R9");
        mListGateR9.add("gate3 R9");

        mListGateCheckPoint.add("Chk1");
        mListGateCheckPoint.add("Chk2");

        mListGate.add("mListGate");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_placa);

        builder.setTitle("Elija Ubicacion y Gate");

        View viewLayout = View.inflate(getContext(), R.layout.spinners_ubicacion, null);
        // esto es el estilo del primer item del spinner que se visualiza
        mAdapterGate = new HighLightArrayAdapter(getContext(), R.layout.spinner_itemstyle, mListGate);
        // esto es el estilo de la lista desplegable
        mAdapterGate.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);


        spn_ubicacion = viewLayout.findViewById(R.id.spn_ubicacion);
        mAdapterUbicacion = new HighLightArrayAdapter(getContext(), R.layout.spinner_itemstyle, mListUbicacion);
        // esto es el estilo de la lista desplegable
        mAdapterUbicacion.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);
        spn_ubicacion.setAdapter(mAdapterUbicacion);
        mAdapterGate.setNotifyOnChange(true);
        spn_ubicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mAdapterUbicacion.setSelection(position);

                String text = spn_ubicacion.getSelectedItem().toString();

                if (text.equals(mListUbicacion.get(0))) {
                    // en caso de usar arrays usar arraycopy
                    // System.arraycopy(mArrGateBananaPuerto, 0, mArrayGate, 0, mArrayGate.length);
                    mListGate.clear();
                    mListGate.addAll(mListGateBananaPuerto);
                }
                if (text.equals(mListUbicacion.get(1))) {
                    mListGate.clear();
                    mListGate.addAll(mListGateR9);
                }
                if (text.equals(mListUbicacion.get(2))) {
                    mListGate.clear();
                    mListGate.addAll(mListGateCheckPoint);
                }

// esto solo es para comprobar, luego eliminar esto
                imprimirDatos();

                System.out.println("adapterUbicacion.toString() " + mAdapterUbicacion.toString());
                mAdapterGate.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_gate = viewLayout.findViewById(R.id.spn_gate);
        spn_gate.setAdapter(mAdapterGate);
        spn_gate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapterGate.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Global.ubicacion_seleccionada = spn_ubicacion.getSelectedItem().toString();
                Global.gate_seleccionado = spn_gate.getSelectedItem().toString();

                Toast.makeText(getContext(), "Ubicacion " + Global.ubicacion_seleccionada, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Gate " + Global.gate_seleccionado, Toast.LENGTH_LONG).show();

                gate.setText(Global.ubicacion_seleccionada + " - " + Global.gate_seleccionado);
                nombre_de_usuario.setText(Global.nombre_de_usuario);
            }
        });

        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setView(viewLayout);


        spn_ubicacion.setSelection(mListUbicacion.indexOf(Global.ubicacion_seleccionada));
        spn_gate.setSelection(mListGate.indexOf(Global.gate_seleccionado));

        return builder.create();
    }

    public void imprimirDatos() {

        for (String dato : mListGate) {
            System.out.println("dato " + dato);
        }
    }

}

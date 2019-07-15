package ec.pure.naportec.eir.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasActivity;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionActivity;

public class DialogoLista extends DialogFragment {

    private final String TAG = "eir." + AddItemSpinnerDialogFragment.class.getSimpleName();

    private final String extra_lista = "extra_lista";
    private List<String> ltsString;

    private TextView txtview;

    private SearchableAdapter mSearchableAdapter;

    public static DialogoLista newInstance(String title, String message) {


        DialogoLista f = new DialogoLista();
        Bundle args = new Bundle();
//        args.putString("message", message);
//        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        String title = getArguments().getString("title");
        ltsString = getArguments().getStringArrayList(extra_lista);

        if (ltsString == null) {
            ltsString = new ArrayList<>();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setIcon(R.drawable.ic_add_to_spinner);

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.table_layout2, null);
        final ListView lv_validate = (ListView) textEntryView.findViewById(R.id.lv_validate);
        final EditText et_buscar = (EditText) textEntryView.findViewById(R.id.et_buscar_table);

        Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
            public int compare(String str1, String str2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
                if (res == 0) {
                    res = str1.compareTo(str2);
                }
                return res;
            }
        };
        Collections.sort(ltsString, ALPHABETICAL_ORDER);
        //inicializamos el arraydapter
        mSearchableAdapter = new SearchableAdapter(getContext(), ltsString);
        lv_validate.setTextFilterEnabled(true);
        lv_validate.setAdapter(mSearchableAdapter);

        lv_validate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("valor seleccionado position " + position);
                txtview = view.findViewById(R.id.listTextView);
                System.out.println("valor " + txtview.getText());

                lv_validate.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
                    @Override
                    public void onSwipeLeft() {
                        // Whatever
                        System.out.println("se desliza a la izquierda ");
                        System.out.println("se desliza el dato " + txtview.getText().toString());
                        // eliminamos el dato del arreglo
                        ltsString.remove(txtview.getText().toString());
                        mSearchableAdapter.notifyDataSetChanged();// actualizamos la lista
                    }
                });
            }
        });

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        System.out.println("Text [" + s + "]");
                mSearchableAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setView(textEntryView); // Another add method

        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                ((RevisionActivity) getActivity()).setDatos(ltsString, true);
            }
        });

        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                ((RevisionActivity) getActivity()).setDatos(null, false);
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
/*     // --> con este chanchuyo no se permite cerrar el dialog sin antes validar algo
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("se hace click en button positive");
            }
        });*/
        // <-- se cierra el chanchuyo
        return dialog;
    }


}

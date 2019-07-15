package ec.pure.naportec.eir.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasAdapter;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos.SellosActivity;

public class AddItemSpinnerDialogFragment extends DialogFragment {

    private final String TAG = "eir." + AddItemSpinnerDialogFragment.class.getSimpleName();

    public static AddItemSpinnerDialogFragment newInstance(String title, String message) {
        AddItemSpinnerDialogFragment f = new AddItemSpinnerDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        String title = getArguments().getString("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setIcon(R.drawable.ic_add_to_spinner);

        //inicia seccion que incluye el edittext en el alertDialog
        final EditText edt_input = new EditText(getContext());
        //con el primer filtro se hacen MAYUSCULAS las letras
        edt_input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_input.setGravity(Gravity.CENTER_HORIZONTAL);// con esto centramos el EditText
        edt_input.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        edt_input.setMaxLines(1);
        edt_input.setSingleLine(true);
        edt_input.setTypeface(null, Typeface.BOLD);
        builder.setView(edt_input); // Another add method
        //finaliza la seccion con la que se incluye el editText

        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // --> hacemos este chanchuyo para que no se cierre el dialog en la validacion
        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset error
                edt_input.setError(null);

                String placa = edt_input.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // validamos que el input no este vacío
                if (TextUtils.isEmpty(placa)) {
                    edt_input.setError(getString(R.string.error_input_vacio));
                    focusView = edt_input;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    if (getTag().equals("eir." + EncabezadoActivity.class.getSimpleName())) {
                        // enviamos el dato al activity
                        ((EncabezadoActivity) getActivity()).setDatos(edt_input.getText().toString());
                    }
                    if (getTag().equals("eir." + SellosActivity.class.getSimpleName())) {
                        // enviamos el dato al activity
                        ((SellosActivity) getActivity()).setDatos(edt_input.getText().toString());
                    }
                    if (getTag().equals("eir." + LlantasAdapter.class.getSimpleName())) {
                        ((LlantasActivity) getActivity()).setDatos(edt_input.getText().toString());
                    }
                    dialog.dismiss();
                }
            }
        });
        // <-- se cierra el chanchuyo
        return dialog;
    }

}

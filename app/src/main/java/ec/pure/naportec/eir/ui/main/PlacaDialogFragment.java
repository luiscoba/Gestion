package ec.pure.naportec.eir.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.utils.MaskWatcher;

public class PlacaDialogFragment extends DialogFragment {

    private final String TAG = "eir." + PlacaDialogFragment.class.getSimpleName();

    private final String extra_evento = "extra_evento";
    private final String extra_evento_int = "extra_evento_int";
    private String mEventoSeleccionado;
    private int mEventoSeleccionadoInt;

    private byte MAX_LENGTH_SPACES = 7; //es el numero maximo de digitos que se pueden ingresar en la placa

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        mEventoSeleccionado = bundle.getString(extra_evento);
        mEventoSeleccionadoInt = bundle.getInt(extra_evento_int);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_placa);
        builder.setIcon(R.drawable.ic_placa);
        builder.setMessage(R.string.text_ingrese_placa);

        //inicia seccion que incluye el edittext en el alertDialog
        final EditText edt_input = new EditText(getContext());
        //con el primer filtro se hacen MAYUSCULAS las letras, con el segunto solo se permite maximo 7 caracteres, con el 3ro se controla la barra espaciadora y caracteres especiales
        edt_input.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(MAX_LENGTH_SPACES), filterBlockCharacterSet});
        edt_input.setGravity(Gravity.CENTER_HORIZONTAL);// con esto centramos el EditText
        edt_input.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        edt_input.setHint(R.string.prompt_placa);
        edt_input.setMaxLines(1);
        edt_input.setSingleLine(true);
        edt_input.addTextChangedListener(new MaskWatcher(edt_input));//aplicamos una mascara a la placa
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

                // validamos placa que no este vacio que sean maximo 6 o 7 digitos
                if (TextUtils.isEmpty(placa) || !isPlacaLongitudValida(placa)) {
                    edt_input.setError(getString(R.string.error_placa_corta));
                    focusView = edt_input;
                    cancel = true;
                }// validamos que los 3 primeros digitos sean letras y el resto numeros
                else if (!isPlacaNumerosLetras(placa)) {
                    edt_input.setError(getString(R.string.error_placa_no_valida));
                    focusView = edt_input;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    dialog.dismiss();

                    ((MainActivity) getActivity()).setDatosDesde_Placa_DialogFragment(
                            edt_input.getText().toString(), //
                            mEventoSeleccionado, //evento que viene desde main y lo envio a main, para mostrarlo en la barra de arriba
                            mEventoSeleccionadoInt);
                }
            }
        });
        // <-- se cierra el chanchuyo
        return dialog;
    }

    private boolean isPlacaLongitudValida(String placa) {
        return placa.length() == 6 || placa.length() == 7; //numero de espacios de la placa
    }

    private boolean isPlacaNumerosLetras(String placa) {
        String placa1 = placa.substring(0, 3); // cogemos los 3 primeros digitos
        String placa2 = placa.substring(4, placa.length()); // cogemos los 4 digitos siguientes

        if (placa1.matches("^[A-Za-z]*$") && placa2.matches("^[0-9]*$")) {
            return true;
        } else {
            return false;
        }
    }

    private InputFilter filterBlockCharacterSet = new InputFilter() {

        private String blockCharacterSet = " ~!@#$%^&*()_+-=,./;'[]<>?;:{}|";// aqui se incluye el espacio en blanco

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }

            return null;
        }
    };

}





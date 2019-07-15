package ec.pure.naportec.eir.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.utils.MaskWatcher;

public class EventoPlacaDialogFragment extends DialogFragment {

    private static final String TAG = "ier." + EventoPlacaDialogFragment.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_evento_int = "extra_evento_int";

    private String[] arr_evento_inspeccion;
    private int opcion_seleccionada = 0;

    private byte MAX_LENGTH_SPACES = 7; //es el numero maximo de digitos que se pueden ingresar en la placa

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_placa);
        builder.setTitle(R.string.title_dialogFragment_inspeccion);

        if (Global.ubicacion_seleccionada.equals("Banana Puerto"))
            arr_evento_inspeccion = getResources().getStringArray(R.array.evento_inspeccion_cerrar_eir);
        else if (Global.ubicacion_seleccionada.equals("R9"))
            arr_evento_inspeccion = getResources().getStringArray(R.array.evento_ubicacion);
        else
            arr_evento_inspeccion = getResources().getStringArray(R.array.evento_inspeccion);

        // --> con esta seccion creamos el TextView y el EditText para obtener la Placa
        Context context = getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        final TextView message = new TextView(context);
        message.setTextSize(18);
        message.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setText(R.string.text_ingrese_placa);
        layout.addView(message); // Notice this is an add method
        //agregamos un edittext al layout que cargamos al alert.dialog
        final EditText edt_input = new EditText(context);
        //con el primer filtro se hacen MAYUSCULAS las letras, con el segunto solo se permite maximo 7 caracteres, con el 3ro se controla la barra espaciadora y caracteres especiales
        edt_input.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(MAX_LENGTH_SPACES), filterBlockCharacterSet});
        edt_input.setGravity(Gravity.CENTER_HORIZONTAL);// con esto centramos el EditText
        edt_input.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        edt_input.setHint(R.string.prompt_placa);
        edt_input.setMaxLines(1);

        edt_input.setSingleLine(true);
        edt_input.addTextChangedListener(new MaskWatcher(edt_input));//aplicamos una mascara a la placa

        edt_input.setTypeface(null, Typeface.BOLD);
        layout.addView(edt_input); // Another add method
        // <-- finaliza la seccion con la que se incluye el textview y el editText
        builder.setView(layout);

        dialogoLista(builder, arr_evento_inspeccion);

        // Add the buttons
        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setNegativeButton(R.string.dialogo_btn_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
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
                    Global.entro_a_encabezado = false;
                    Global.entro_a_contenedor = false;
                    Global.entro_a_chassis = false;
                    Global.entro_a_sellos = false;
                    Global.entro_a_llantas = false;

                    dialog.dismiss();
                    ((MainActivity) getActivity()).setDatosDesde_EventoPlaca_DialogFragment(
                            edt_input.getText().toString(),
                            arr_evento_inspeccion[opcion_seleccionada],
                            opcion_seleccionada);
                }
            }

        });
        // <-- se cierra el chanchuyo
        return dialog;
    }

    private void dialogoLista(AlertDialog.Builder builder, final String[] eventos) {
        builder.setSingleChoiceItems(eventos, opcion_seleccionada, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                opcion_seleccionada = which;
                //launch_Inspeccion(eventos[opcion_seleccionada]);
            }
        });
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

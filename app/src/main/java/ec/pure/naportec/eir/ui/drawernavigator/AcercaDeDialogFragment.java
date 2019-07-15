package ec.pure.naportec.eir.ui.drawernavigator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import ec.pure.naportec.eir.R;

public class AcercaDeDialogFragment extends DialogFragment {

    private static final String TAG = "eir." + AcercaDeDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_acerca_de_eir);

        // --> con esta seccion creamos el TextView y el EditText para obtener la Placa
        Context context = getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Agregamos la imagen como un logo
        final ImageView img_logo = new ImageView(context);
        img_logo.setImageResource(R.drawable.logo_pure);
        layout.addView(img_logo); // Another add method

        // Add a TextView here for the "Title" label, as noted in the comments
        final TextView txv_mensaje = new TextView(context);
        txv_mensaje.setTextSize(12);
        txv_mensaje.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        txv_mensaje.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txv_mensaje.setText(R.string.text_acerca_de_detalle);
        layout.addView(txv_mensaje); // Notice this is an add method

        builder.setView(layout);
        // <-- finaliza la seccion con la que se incluye el textview y el editText

        // Add the buttons
        builder.setPositiveButton(R.string.dialogo_btn_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

}

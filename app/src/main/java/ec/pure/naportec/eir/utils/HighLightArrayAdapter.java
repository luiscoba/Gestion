package ec.pure.naportec.eir.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

// esta clase sirve para resaltar el color en el item seleccionado de los Spinners
// tambien sobreescribe en el ArrayAdapter para recibir una Lista de objetos
public class HighLightArrayAdapter extends ArrayAdapter<CharSequence> {

    private int mSelectedIndex = -1;

    public void setSelection(int position) {
        mSelectedIndex = position;
        notifyDataSetChanged();
    }

    public HighLightArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getDropDownView(position, convertView, parent);

        if (position == mSelectedIndex) {
            itemView.setBackgroundColor(Color.rgb(56, 184, 226));
/*        } else {
            itemView.setBackgroundColor(Color.TRANSPARENT);
*/
        }

        return itemView;
    }

    @Override// hacemos esto para mostrar, cargar un elemento menos,
    // posteriormente esto en el activity nos servira para mostrar el mensaje de "Seleccione ..."
    public int getCount() {
        return super.getCount();
    }
}

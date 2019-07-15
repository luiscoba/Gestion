package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.grid_imagen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;

public class ImagenAdapter extends BaseAdapter {

    private static final String TAG_ImagenAdapter = "eir." + ImagenAdapter.class.getSimpleName();

    private List<ContenedorInspeccion> lstContenedoresTab1;

    private TypedArray array_de_imagenes;

    private LayoutInflater this_Inflater;

    public ImagenAdapter(Context context, TypedArray nombres_de_imagenes, List<ContenedorInspeccion> lstContenedoresTab1) {
        this.this_Inflater = LayoutInflater.from(context);
        this.array_de_imagenes = nombres_de_imagenes;
        this.lstContenedoresTab1 = lstContenedoresTab1;
    }

    @Override
    public int getCount() {
        return array_de_imagenes.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = this_Inflater.inflate(R.layout.grid_item, parent, false);
            ImageView thumbnailImage = convertView.findViewById(R.id.thumbnail_image);
            // al array_de_imagenes se accede con .getResourceId(position, -1)
            thumbnailImage.setImageResource(array_de_imagenes.getResourceId(position, -1));

/*
            if (!lstContenedoresTab1.get(position).getDaniado().isEmpty())
                if (Integer.parseInt(lstContenedoresTab1.get(position).getEiridcPosic()) == position) {
                    TextView textoActual = convertView.findViewById(R.id.txt_imagen);
                    textoActual.setTextColor(Color.RED);
                    textoActual.setVisibility(View.VISIBLE);

                    textoActual.setText("comer ");
                }
*/
        }

        return convertView;
    }

}

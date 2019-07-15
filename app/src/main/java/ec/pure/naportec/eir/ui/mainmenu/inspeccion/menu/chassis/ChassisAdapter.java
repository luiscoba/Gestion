package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.chassis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.common.collect.BiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ec.pure.naportec.eir.R;

public class ChassisAdapter extends ArrayAdapter<String> {

    private static final String TAG_LlantasAdapter = "eir." + ChassisAdapter.class.getSimpleName();

    List<String> mParentesis;
    List<String> mLstDescripciones;
    BiMap<String, String> biMap_uuid_descripcion_Referencia;
    Map<String, Boolean> map_uuid_estado;
    ArrayList<String> mLstUuid;

    Context mContext;

    public ChassisAdapter(Context context, int resourceId, BiMap<String, String> hash_map_checks, List<String> mLstDescripciones, ArrayList<String> mLstUuid, List<String> mParentesis, Map<String, Boolean> map_uuid_estado) {
        super(context, resourceId, mParentesis);

        this.mContext = context;
        this.mLstDescripciones = mLstDescripciones;
        this.biMap_uuid_descripcion_Referencia = hash_map_checks;
        this.mParentesis = mParentesis;
        this.map_uuid_estado = map_uuid_estado;
        this.mLstUuid = mLstUuid;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_chassis, null);
        TextView textView = v.findViewById(R.id.txtv_parentesis);
        CheckBox chkbChassis = v.findViewById(R.id.chkb_chassis);
        chkbChassis.setText(mLstDescripciones.get(position));//son las descripciones que vienen de referencias
        if (map_uuid_estado.containsKey(biMap_uuid_descripcion_Referencia.inverse().get(mLstDescripciones.get(position))))
            chkbChassis.setChecked(map_uuid_estado.get(mLstUuid.get(position)));

        textView.setText(mParentesis.get(position));

        return v;
    }

}
package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.google.common.collect.BiMap;

import java.util.List;

import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.remote.model.LlantaInspeccion;
import ec.pure.naportec.eir.utils.AddItemSpinnerDialogFragment;
import ec.pure.naportec.eir.utils.HighLightArrayAdapterCountMenos1;

public class LlantasAdapter extends ArrayAdapter<String> implements AdapterView.OnItemSelectedListener {

    private static final String TAG_LlantasAdapter = "eir." + LlantasAdapter.class.getSimpleName();

    List<String> mPosicsList;
    List<String> mMarcasList;
    List<String> mEstadosList;
    List<LlantaInspeccion> lstLlantas;
    BiMap<String, String> biMapMarca_uuid_descripcion;
    BiMap<String, String> biMapEstado_uuid_descripcion;

    Context mContext;
    FragmentManager FragManager;

    public LlantasAdapter(Context context, int resourceId, List<String> posicsString, List<String> marcasString, BiMap<String, String> biMapMarca_uuid_descripcion, List<String> estadosString, BiMap<String, String> biMapEstado_uuid_descripcion, List<LlantaInspeccion> lstLlantas, FragmentManager FragManager) {
        super(context, resourceId, posicsString);

        this.mContext = context;
        this.mPosicsList = posicsString;
        this.mMarcasList = marcasString;
        this.biMapMarca_uuid_descripcion = biMapMarca_uuid_descripcion;
        this.biMapEstado_uuid_descripcion = biMapEstado_uuid_descripcion;
        this.mEstadosList = estadosString;
        this.lstLlantas = lstLlantas;
        this.FragManager = FragManager;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String idEstado = null, idMarca = null;

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_llanta, null);
        TextView textView = (TextView) v.findViewById(R.id.textViewPosicion);
        Spinner spnMarca = v.findViewById(R.id.spn_marca);
        Spinner spnEstado = v.findViewById(R.id.spn_estado);
        textView.setText(mPosicsList.get(position));

        if (lstLlantas != null) {
            idMarca = lstLlantas.get(position).getGenrciUuid2();
            idEstado = lstLlantas.get(position).getGenrciUuid3();
        }
        // cargamos cada Spinner con la correspondiente lista
        cargarSpinnerMarca(spnMarca, mMarcasList, idMarca);// lstLlantas.get(position).getGenrciUuid2());
        cargarSpinnerEstado(spnEstado, mEstadosList, idEstado);
//        System.out.println("textView " + textView.getText().toString());
        return v;
    }

    //spnMarca.setSelection(2);//mLlantasMarca.indexOf(lstLlantas.get(i).getGenrciUuid2()));
    public void cargarSpinnerMarca(Spinner spinner, List<String> lista, String idMarca) {
        HighLightArrayAdapterCountMenos1 mAdapter = new HighLightArrayAdapterCountMenos1(mContext, R.layout.spinner_itemstyle, lista);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);
        spinner.setAdapter(mAdapter);
        if (idMarca == null)
            spinner.setSelection(mAdapter.getCount());//index starts from 0. so if spinner has 5 item the lastIndex is 4
        else
            spinner.setSelection(lista.indexOf(biMapMarca_uuid_descripcion.get(idMarca)));//mAdapter.getCount());//index starts from 0. so if spinner has 5 item the lastIndex is 4
//        System.out.println("biMapMarca_uuid_descripcion.get(idMarca) " + biMapMarca_uuid_descripcion.get(idMarca));
//        System.out.println("lstLlantas.indexOf(biMapMarca_uuid_descripcion.get(idMarca)) " + lista.indexOf(biMapMarca_uuid_descripcion.get(idMarca)));
        spinner.setOnItemSelectedListener(this);
    }

    public void cargarSpinnerEstado(Spinner spinner, List<String> lista, String idEstado) {
        HighLightArrayAdapterCountMenos1 mAdapter = new HighLightArrayAdapterCountMenos1(mContext, R.layout.spinner_itemstyle, lista);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_itemstyle);
        spinner.setAdapter(mAdapter);
        if (idEstado == null)
            // mostramos el lastIndex
            spinner.setSelection(mAdapter.getCount());//index starts from 0. so if spinner has 5 item the lastIndex is 4
        else
            spinner.setSelection(lista.indexOf(biMapEstado_uuid_descripcion.get(idEstado)));
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        int position = listView.getPositionForView(parentRow);
//        System.out.println("position " + getPosition(parent.getItemAtPosition(position).toString()));
//        System.out.println("mPosicsList.get(position) " + mPosicsList.get(position));
//        System.out.println("lLantasAdapterparent.getItemAtPosition(position) " + parent.getItemAtPosition(position));
        if (parent.getItemAtPosition(position).toString().equals("Agregar nuevo valor...")) {
            AddItemSpinnerDialogFragment dialogAgregar = AddItemSpinnerDialogFragment.newInstance("MARCA", "Agregue una nueva marca");

            dialogAgregar.show(LlantasAdapter.this.FragManager, TAG_LlantasAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}
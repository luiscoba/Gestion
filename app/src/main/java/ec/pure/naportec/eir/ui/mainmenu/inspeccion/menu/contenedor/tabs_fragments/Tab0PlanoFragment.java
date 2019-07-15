package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import ec.pure.naportec.eir.R;

public class Tab0PlanoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plano, container, false);
    }
}
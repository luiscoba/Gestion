package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";

    private String mPlacaIngresada;
    private String mEventoSeleccionado;

    public TabAdapter(FragmentManager fm, String placaIngresada, String eventoSeleccionado) {
        super(fm);
        this.mPlacaIngresada = placaIngresada;
        this.mEventoSeleccionado = eventoSeleccionado;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle mBundle = new Bundle();
        mBundle.putString(extra_placa, mPlacaIngresada);
        mBundle.putString(extra_evento, mEventoSeleccionado);
        mFragmentList.get(position).setArguments(mBundle);
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }

}
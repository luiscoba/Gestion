package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab0PlanoFragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab1Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab2Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab3Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab4Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab5Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab6Fragment;
import ec.pure.naportec.eir.utils.PersonalizadoToolBar;

public class ContenedorActivity extends PersonalizadoToolBar implements HasSupportFragmentInjector {

    private static final String TAG_ContenedorActivity = "eir." + ContenedorActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    private InspeccionEntity inspeccionEntity;
    private String mEiricbUuid;

    private List<ContenedorInspeccion> contenedorInspeccions;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelFactory viewModelFactory;
    ContenedorSyncViewModel contenedorSyncViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        contenedorSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContenedorSyncViewModel.class);

        inspeccionEntity = contenedorSyncViewModel.obtenerInspeccion();
        mEiricbUuid = inspeccionEntity.getEiricbUuid();//obtenemos el id de la inspeccion, que posteriormente nos servirá para actualizar el EIR

        contenedorInspeccions = inspeccionEntity.getContenedor();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);

        Global.entro_a_contenedor = true;

        setViews();

        dataInjection();

        ponerPlacaYevento();
    }

    private void ponerPlacaYevento() {
        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);
    }

    private void setViews() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), mPlacaIngresada, mEventoSeleccionado);

        adapter.addFragment(new Tab0PlanoFragment(), "Plano");
        adapter.addFragment(new Tab1Fragment(), "Tab 1");
        adapter.addFragment(new Tab2Fragment(), "Tab 2");
        adapter.addFragment(new Tab3Fragment(), "Tab 3");
        adapter.addFragment(new Tab4Fragment(), "Tab 4");
        adapter.addFragment(new Tab5Fragment(), "Tab 5");
        adapter.addFragment(new Tab6Fragment(), "Tab 6");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}

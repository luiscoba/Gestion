package ec.pure.naportec.eir.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.R;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.drawernavigator.AcercaDeDialogFragment;
import ec.pure.naportec.eir.ui.drawernavigator.ubicacion.UbicacionGateDialogFragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.InspeccionActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.InspeccionViewModel;
import ec.pure.naportec.eir.ui.mainmenu.patio.PatioActivity;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionActivity;
import ec.pure.naportec.eir.ui.mainmenu.taller.TallerActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String tag = "eir." + MainActivity.class.getSimpleName();

    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private final String extra_evento_int = "extra_evento_int";

    private final String strDespachoActivity = "Despacho";
    private final String strMecanicaActivity = "Mecánica";
    private final String strRevisionActivity = "Revisión";

    private final int mDespacho_opcion = 3;
    private final int mRevision_opcion = 4;
    private final int mMecanica_opcion = 5;

    private ProgressBar progressBar;

    private LinearLayout inspeccion, despacho, revision, mecanica;

    private TextView usuario, gate;

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel mainViewModel;
    MainSyncViewModel mainSyncViewModel;
    InspeccionViewModel inspeccionViewModel;

    private void dataInjection() {
        AndroidInjection.inject(this);
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        mainSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainSyncViewModel.class);
        inspeccionViewModel = ViewModelProviders.of(this, viewModelFactory).get(InspeccionViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuario.setText(Global.nombre_de_usuario);
        gate.setText(Global.ubicacion_seleccionada + " - " + Global.gate_seleccionado);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();

        dataInjection();

        inspeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_EventoPlacaDialogFragment();
            }
        });

        despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global.ubicacion_seleccionada.equals("Banana Puerto"))
                    launch_PlacaDialogFragment(strDespachoActivity, mDespacho_opcion);
                else
                    Toast.makeText(getApplicationContext(), "Opción no permitida, en la ubicación seleccionada", Toast.LENGTH_LONG).show();
            }
        });

        revision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.ubicacion_seleccionada.equals("Banana Puerto"))
                    launch_PlacaDialogFragment(strRevisionActivity, mRevision_opcion); //es el evento 4 que se enviará para posteriormente consultar
                else
                    Toast.makeText(getApplicationContext(), "Opción no permitida, en la ubicación seleccionada", Toast.LENGTH_LONG).show();
            }
        });

        mecanica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.ubicacion_seleccionada.equals("Banana Puerto"))
                    launch_PlacaDialogFragment(strMecanicaActivity, mMecanica_opcion);
                else
                    Toast.makeText(getApplicationContext(), "Opción no permitida, en la ubicación seleccionada", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = findViewById(R.id.txv_nombre_de_usuario);
        gate = findViewById(R.id.txv_gate);

        inspeccion = findViewById(R.id.layout_inspeccion);
        despacho = findViewById(R.id.layout_despacho);
        revision = findViewById(R.id.layout_revision);
        mecanica = findViewById(R.id.layout_mecanica);

        progressBar = findViewById(R.id.progress_bar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void launch_UbicacionGateDialogFragment() {
        UbicacionGateDialogFragment ubicacionGateDialogFragment = new UbicacionGateDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("titulo", "Placa");
        ubicacionGateDialogFragment.setArguments(mBundle);
        ubicacionGateDialogFragment.show(getSupportFragmentManager(), UbicacionGateDialogFragment.class.getSimpleName());
    }

    private void launch_EventoPlacaDialogFragment() {
        EventoPlacaDialogFragment placaDialogFragment = new EventoPlacaDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("titulo", "Placa");
        placaDialogFragment.setArguments(mBundle);
        placaDialogFragment.show(getSupportFragmentManager(), EventoPlacaDialogFragment.class.getSimpleName());
    }

    private void launch_PlacaDialogFragment(String activity_evento_seleccionado, int evento_int) {
        PlacaDialogFragment placaDialogFragment = new PlacaDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(extra_evento, activity_evento_seleccionado);
        mBundle.putInt(extra_evento_int, evento_int);
        placaDialogFragment.setArguments(mBundle);
        placaDialogFragment.show(getSupportFragmentManager(), PlacaDialogFragment.class.getSimpleName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ubicacion) {
            launch_UbicacionGateDialogFragment();
        } else if (id == R.id.nav_sincronizacion) {

        } else if (id == R.id.nav_cambiar_usuario) {

        } else if (id == R.id.nav_cerrar_sesion) {

        } else if (id == R.id.nav_acerca_de) {
            launch_AcercaDeDialogFragment();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launch_AcercaDeDialogFragment() {
        AcercaDeDialogFragment acercaDeDialogFragment = new AcercaDeDialogFragment();
        acercaDeDialogFragment.show(getSupportFragmentManager(), "PlacaDialogFragment");
    }

    // aqui hay que controlar que en evento_seleccionado_int, venga 1 y 2
    public void setDatosDesde_EventoPlaca_DialogFragment(String placa_ingresada, String evento_seleccionado, int evento_seleccionado_int) {

        evento_seleccionado_int = evento_seleccionado_int + 1;// sumamos 1 porque la seleccion del array de opciones mostrada empieza en cero
        if (evento_seleccionado_int == 3)
            evento_seleccionado_int = 0;

        mainViewModel.obtenerInspeccionDesde_EventoPlaca().removeObservers(this); // remueve todos los observers, que están tratando de acceder al cyclelife
        mainViewModel.consultarInspeccionDesde_EventoPlaca(placa_ingresada, evento_seleccionado_int, null, true);
        int finalEvento_seleccionado_int = evento_seleccionado_int;
        mainViewModel.obtenerInspeccionDesde_EventoPlaca().observe(this, resource -> {
            if (resource.isLoading()) {
            } else if (resource.data != null) {
                // con esto controlamos que no vuelva a llamar al observer, que observa finalEvento_seleccionado_int igual a 4, 5 y 6
                if (finalEvento_seleccionado_int != mDespacho_opcion && finalEvento_seleccionado_int != mMecanica_opcion && finalEvento_seleccionado_int != mRevision_opcion)
                    if (finalEvento_seleccionado_int == 0) {
                        if (resource.data.getEstado() == 1) {
                            launch_InspeccionActivity(placa_ingresada, evento_seleccionado, finalEvento_seleccionado_int);
                        }
                    } else {
                        launch_InspeccionActivity(placa_ingresada, evento_seleccionado, finalEvento_seleccionado_int);
                    }

                Toast.makeText(getApplicationContext(), resource.data.getMensaje(), Toast.LENGTH_LONG).show();

            } else handleErrorResponse();

            resource.data = null;
        });
    }

    private void handleErrorResponse() {
        System.out.println("log.error error de conexion ");
        System.out.println("log.error error de conexion ");
    }

    /**
     * Lanzamos el activity Inspeccion
     */
    private void launch_InspeccionActivity(String placa_ingresada, String evento_seleccionado, int evento_seleccionado_int) {
        /* hacemos esto para crear un univo activity, un activity Singleton */
        final Intent intent_inspeccion = new Intent(this, InspeccionActivity.class);
        intent_inspeccion.setAction(Intent.ACTION_MAIN);
        intent_inspeccion.addCategory(Intent.CATEGORY_LAUNCHER);

        intent_inspeccion.putExtra(extra_placa, placa_ingresada);
        intent_inspeccion.putExtra(extra_evento, evento_seleccionado);
        intent_inspeccion.putExtra(extra_evento_int, evento_seleccionado_int);
        //startActivity(new Intent(this, InspeccionActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)); //con esto se crea un Singleton Activity
        startActivity(intent_inspeccion.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    // aqui hay que controlar que en evento_seleccionado_int, venga 3,4 y 5
    public void setDatosDesde_Placa_DialogFragment(String placa_ingresada, String evento_seleccionado, int evento_seleccionado_int) {// en evento_seleccionado_int, viene 3,4 y 5

        mainSyncViewModel.borrarRespuestaEntity();

        mainViewModel.consultarInspeccionDesde_Placa(placa_ingresada, evento_seleccionado_int, null, true);
        mainViewModel.obtenerInspeccionDesde_Placa().removeObservers(this); // remueve todos los observers, que están tratando de acceder al cyclelife
        mainViewModel.obtenerInspeccionDesde_Placa().observe(this, resource -> {
            if (resource.isLoading()) {
            } else if (resource.data != null) {
                if (resource.data.getEstado() == 1)
                    launch_activity(placa_ingresada, evento_seleccionado, evento_seleccionado_int);//finalEvento_seleccionado_int);
                Toast.makeText(getApplicationContext(), resource.data.getMensaje(), Toast.LENGTH_LONG).show();

            } else handleErrorResponse();

            resource.data = null;
        });

    }

    public void launch_activity(String placa_ingresada, String evento_seleccionado, int evento_seleccionado_int) {

        if (evento_seleccionado.equals(strDespachoActivity)) {

            final Intent intent = new Intent(this, PatioActivity.class);
            intent.putExtra(extra_placa, placa_ingresada);
            intent.putExtra(extra_evento, evento_seleccionado);
            intent.putExtra(extra_evento_int, evento_seleccionado_int);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (evento_seleccionado.equals(strMecanicaActivity)) {

            final Intent intent = new Intent(this, TallerActivity.class);
            intent.putExtra(extra_placa, placa_ingresada);
            intent.putExtra(extra_evento, evento_seleccionado);
            intent.putExtra(extra_evento_int, evento_seleccionado_int);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (evento_seleccionado.equals(strRevisionActivity)) {

            final Intent intent = new Intent(this, RevisionActivity.class);
            intent.putExtra(extra_placa, placa_ingresada);
            intent.putExtra(extra_evento, evento_seleccionado);
            intent.putExtra(extra_evento_int, evento_seleccionado_int);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        }

    }

}
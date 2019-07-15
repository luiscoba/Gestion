package ec.pure.naportec.eir.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ec.pure.naportec.eir.ui.drawernavigator.sesion.LoginActivity;
import ec.pure.naportec.eir.ui.main.MainActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.InspeccionActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.chassis.ChassisActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.ContenedorActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasActivity;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos.SellosActivity;
import ec.pure.naportec.eir.ui.mainmenu.patio.PatioActivity;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionActivity;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.taller.TallerActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract SellosActivity contributeSellosActivity();

    @ContributesAndroidInjector()
    abstract LlantasActivity contributeLlantasActivity();

    @ContributesAndroidInjector()
    abstract EncabezadoActivity contributeEncabezadoActivity();

    @ContributesAndroidInjector()
    abstract ChassisActivity contributeChassisActivity();

    @ContributesAndroidInjector()
    abstract ContenedorActivity contributeContenedorActivity();

    @ContributesAndroidInjector()
    abstract InspeccionActivity contributeInspeccionActivity();

    @ContributesAndroidInjector()
    abstract PatioActivity contributePatioActivity();

    @ContributesAndroidInjector()
    abstract TallerActivity contributeTallerActivity();

    @ContributesAndroidInjector()
    abstract RevisionActivity contributeRevisionActivity();

}

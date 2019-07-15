package ec.pure.naportec.eir.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ec.pure.naportec.eir.ui.drawernavigator.ubicacion.UbicacionGateDialogFragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.Tab1Fragment;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosDialogFragment;
import ec.pure.naportec.eir.utils.AddItemSpinnerDialogFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract UbicacionGateDialogFragment contributeUbicacionGateDialogFragment();

    @ContributesAndroidInjector()
    abstract AddItemSpinnerDialogFragment contributeAddItemSpinnerDialogFragment();

    @ContributesAndroidInjector()
    abstract DañosDialogFragment contributeDañosDialogFragment();

    @ContributesAndroidInjector()
    abstract Tab1Fragment contributeTab1Fragment();

}

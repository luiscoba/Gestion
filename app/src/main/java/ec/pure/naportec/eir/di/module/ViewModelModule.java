package ec.pure.naportec.eir.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.main.MainSyncViewModel;
import ec.pure.naportec.eir.ui.main.MainViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.InspeccionSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.InspeccionViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.chassis.ChassisSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.ContenedorSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.tabs_fragments.util.DañosSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.encabezado.EncabezadoViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas.LlantasViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos.SellosSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.sellos.SellosViewModel;
import ec.pure.naportec.eir.ui.mainmenu.patio.PatioSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.patio.PatioViewModel;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.revision.RevisionViewModel;
import ec.pure.naportec.eir.ui.mainmenu.taller.TallerSyncViewModel;
import ec.pure.naportec.eir.ui.mainmenu.taller.TallerViewModel;
import ec.pure.naportec.eir.ui.viewmodel.DatabaseViewModel;
import ec.pure.naportec.eir.ui.drawernavigator.sesion.ParamciaViewModel;
import ec.pure.naportec.eir.ui.drawernavigator.sesion.ReferenciaViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(ReferenciaViewModel.class)
    protected abstract ViewModel referenciaViewModel(ReferenciaViewModel referenciaViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ParamciaViewModel.class)
    protected abstract ViewModel paramciaViewModel(ParamciaViewModel paramciaViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DatabaseViewModel.class)
    protected abstract ViewModel databaseViewModel(DatabaseViewModel databaseViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SellosViewModel.class)
    protected abstract ViewModel sellosViewModel(SellosViewModel sellosViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LlantasViewModel.class)
    protected abstract ViewModel llantasViewModel(LlantasViewModel llantasViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EncabezadoViewModel.class)
    protected abstract ViewModel encabezadoViewModel(EncabezadoViewModel encabezadoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DañosSyncViewModel.class)
    protected abstract ViewModel dañosViewModel(DañosSyncViewModel dañosSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    protected abstract ViewModel mainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EncabezadoSyncViewModel.class)
    protected abstract ViewModel encabezadoSyncViewModel(EncabezadoSyncViewModel encabezadoSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChassisSyncViewModel.class)
    protected abstract ViewModel chassisSyncViewModel(ChassisSyncViewModel chassisSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LlantasSyncViewModel.class)
    protected abstract ViewModel llantasSyncViewModel(LlantasSyncViewModel llantasSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SellosSyncViewModel.class)
    protected abstract ViewModel sellosSyncViewModel(SellosSyncViewModel sellosSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(InspeccionViewModel.class)
    protected abstract ViewModel inspeccionViewModel(InspeccionViewModel inspeccionViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContenedorSyncViewModel.class)
    protected abstract ViewModel contenedorSyncViewModel(ContenedorSyncViewModel contenedorSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(InspeccionSyncViewModel.class)
    protected abstract ViewModel inspeccionSyncViewModel(InspeccionSyncViewModel inspeccionSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainSyncViewModel.class)
    protected abstract ViewModel mainSyncViewModel(MainSyncViewModel mainSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PatioViewModel.class)
    protected abstract ViewModel patioViewModel(PatioViewModel patioViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PatioSyncViewModel.class)
    protected abstract ViewModel patioSyncViewModel(PatioSyncViewModel patioSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TallerViewModel.class)
    protected abstract ViewModel tallerViewModel(TallerViewModel tallerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TallerSyncViewModel.class)
    protected abstract ViewModel tallerSyncViewModel(TallerSyncViewModel tallerSyncViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RevisionViewModel.class)
    protected abstract ViewModel revisionViewModel(RevisionViewModel revisionViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RevisionSyncViewModel.class)
    protected abstract ViewModel revisionSyncViewModel(RevisionSyncViewModel revisionSyncViewModel);

}
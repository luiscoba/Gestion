package ec.pure.naportec.eir.ui.main;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.RespuestaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.repository.InspeccionRepository;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    private InspeccionRepository inspeccionRepository;
    private MutableLiveData<Resource<RespuestaEntity>> rscInspeccion_desde_EventoPlaca_MutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<RespuestaEntity>> rscInspeccion_desde_PlacaMutableLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(InspeccionDao inspeccionDao, ApiServiceDole apiService) {
        inspeccionRepository = new InspeccionRepository(inspeccionDao, apiService);
    }

    public MutableLiveData<Resource<RespuestaEntity>> obtenerInspeccionDesde_EventoPlaca() {
        return rscInspeccion_desde_EventoPlaca_MutableLiveData;
    }

    public MutableLiveData<Resource<RespuestaEntity>> obtenerInspeccionDesde_Placa() {
        return rscInspeccion_desde_PlacaMutableLiveData;
    }

    public void consultarInspeccionDesde_EventoPlaca(String iprec_placa, int tipo, InspeccionEntity inspeccionEntity, boolean fetch) {
        inspeccionRepository.bajarInspeccion(iprec_placa, tipo, null, fetch)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerInspeccionDesde_EventoPlaca().postValue(resource));
    }

    public void consultarInspeccionDesde_Placa(String iprec_placa, int tipo, InspeccionEntity inspeccionEntity, boolean fetch) {
        inspeccionRepository.bajarInspeccion(iprec_placa, tipo, null, fetch)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerInspeccionDesde_Placa().postValue(resource));
    }

}

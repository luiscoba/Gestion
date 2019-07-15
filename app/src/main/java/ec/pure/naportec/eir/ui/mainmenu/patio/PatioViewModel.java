package ec.pure.naportec.eir.ui.mainmenu.patio;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.RespuestaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.repository.InspeccionRepository;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class PatioViewModel extends BaseViewModel {

    private InspeccionRepository inspeccionRepository;
    private MutableLiveData<Resource<RespuestaEntity>> rscInspeccionEIRMutableLiveData = new MutableLiveData<>();

    @Inject
    public PatioViewModel(InspeccionDao inspeccionDao, ApiServiceDole apiServiceDole) {
        inspeccionRepository = new InspeccionRepository(inspeccionDao, apiServiceDole);
    }


    public MutableLiveData<Resource<RespuestaEntity>> obtenerRespuestaDeSubirInspeccionLiveData() {
        return rscInspeccionEIRMutableLiveData;
    }

    public void actualizarInspeccion(InspeccionEntity inspeccionEntity) {
        inspeccionRepository.subirInspeccion(inspeccionEntity)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerRespuestaDeSubirInspeccionLiveData().postValue(resource));
    }


}

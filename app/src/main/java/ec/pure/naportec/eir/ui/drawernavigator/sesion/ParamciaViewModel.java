package ec.pure.naportec.eir.ui.drawernavigator.sesion;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.dao.ParamciaDao;
import ec.pure.naportec.eir.data.local.entity.ParamciaEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.repository.ParamciaRepository;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class ParamciaViewModel extends BaseViewModel {

    private ParamciaRepository paramciaRepository;
    private MutableLiveData<Resource<List<ParamciaEntity>>> rscListParamciaMutableLiveData = new MutableLiveData<>();

       @Inject
    public ParamciaViewModel(ParamciaDao paramciaDao, ApiServiceDole apiServiceDole) {
        paramciaRepository = new ParamciaRepository(paramciaDao, apiServiceDole);
    }

    public void cargarTablaParamcia() {
        paramciaRepository.cargarParamciaEntity()
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerTodaParamciaLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<ParamciaEntity>>> obtenerTodaParamciaLiveData() {
        return rscListParamciaMutableLiveData;
    }

}

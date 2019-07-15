package ec.pure.naportec.eir.ui.drawernavigator.sesion;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import javax.inject.Inject;
import ec.pure.naportec.eir.data.repository.ReferenciaRepository;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class ReferenciaViewModel extends BaseViewModel {

    private ReferenciaRepository referenciaRepository;
    private MutableLiveData<Resource<List<ReferenciaEntity>>> rscListReferenciaMutableLiveData = new MutableLiveData<>();

    @Inject
    public ReferenciaViewModel(ReferenciaDao referenciaDao, ApiServiceDole apiServiceDole) {
        referenciaRepository = new ReferenciaRepository(referenciaDao, apiServiceDole);
    }

    public void cargarTablaReferencia() {
        referenciaRepository.cargarTablaReferencia()
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerTodaReferenciaLiveData().postValue(resource));
    }

   public MutableLiveData<Resource<List<ReferenciaEntity>>> obtenerTodaReferenciaLiveData() {
        return rscListReferenciaMutableLiveData;
    }

}

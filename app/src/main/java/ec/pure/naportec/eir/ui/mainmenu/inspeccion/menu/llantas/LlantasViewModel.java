package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.llantas;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.repository.ReferenciaRepository;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class LlantasViewModel extends BaseViewModel {

    private ReferenciaRepository referenciaRepository;
    private MutableLiveData<Resource<List<ReferenciaEntity>>> rscListReferenciaMutableLiveData = new MutableLiveData<>();

    @Inject
    public LlantasViewModel(ReferenciaDao referenciaDao, ApiServiceDole apiServiceDole) {
        referenciaRepository = new ReferenciaRepository(referenciaDao, apiServiceDole);
    }

    public MutableLiveData<Resource<List<ReferenciaEntity>>> obtenerTodaReferenciaLiveData() {
        return rscListReferenciaMutableLiveData;
    }

    public void insertarReferencia(String genrci_descripcion, String genmodCodigo, String gentrfCodigo, String orderBy,boolean isFetch) {
        referenciaRepository.insertarReferenciaEntity(genrci_descripcion, genmodCodigo, gentrfCodigo,orderBy, isFetch)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> obtenerTodaReferenciaLiveData().postValue(resource));
    }

}

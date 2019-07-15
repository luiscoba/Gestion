package ec.pure.naportec.eir.ui.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import ec.pure.naportec.eir.data.local.AppDatabase;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class DatabaseViewModel extends BaseViewModel {

    private ReferenciaDao referenciaDao;

    @Inject
    public DatabaseViewModel(@NonNull Application application) {
        referenciaDao = AppDatabase.getInstance(application).referenciaDao();
    }

    public LiveData<List<ReferenciaEntity>> obtenerReferencia(String genmodCodigo, String gentrfCodigo) {
        return referenciaDao.obtenerReferenciasOrderByDescription(genmodCodigo, gentrfCodigo);
    }

}

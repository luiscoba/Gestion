package ec.pure.naportec.eir.ui.mainmenu.revision;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.AppDatabase;
import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class RevisionSyncViewModel extends BaseViewModel {

    private InspeccionDao inspeccionDao;
    private ReferenciaDao referenciaDao;

    @Inject
    public RevisionSyncViewModel(@NonNull Application application) {
        inspeccionDao = AppDatabase.getInstance(application).inspeccionDao();
        referenciaDao = AppDatabase.getInstance(application).referenciaDao();
    }

    public InspeccionEntity obtenerInspeccion() {
        return inspeccionDao.obtenerInspeccionEntity();
    }

    public void actualizarInspeccion(InspeccionEntity entity) {
        inspeccionDao.actualizarInspeccion(entity);
    }

    public LiveData<List<ReferenciaEntity>> obtenerReferencias(String genmodCodigo, String gentrfCodigo) {
        return referenciaDao.obtenerReferenciasOrderByDatoNum1(genmodCodigo, gentrfCodigo);
    }
}

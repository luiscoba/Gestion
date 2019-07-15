package ec.pure.naportec.eir.ui.mainmenu.inspeccion;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ec.pure.naportec.eir.data.local.AppDatabase;
import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.ui.base.BaseViewModel;

public class InspeccionSyncViewModel extends BaseViewModel {

    private InspeccionDao inspeccionDao;
    private ReferenciaDao referenciaDao;

    @Inject
    public InspeccionSyncViewModel(@NonNull Application application) {
        inspeccionDao = AppDatabase.getInstance(application).inspeccionDao();
        referenciaDao = AppDatabase.getInstance(application).referenciaDao();
    }

    public List<ReferenciaEntity> obtenerReferencia(String genmodCodigo, String gentrfCodigo) {
        return referenciaDao.obtenerListaReferenciasOrderByDescription(genmodCodigo,gentrfCodigo);
    }

    public InspeccionEntity obtenerInspeccion() {
        return inspeccionDao.obtenerInspeccionEntity();
    }

    public void actualizarInspeccion(InspeccionEntity entity) {
        inspeccionDao.actualizarInspeccion(entity);
    }
}


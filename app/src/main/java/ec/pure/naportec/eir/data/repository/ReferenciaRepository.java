package ec.pure.naportec.eir.data.repository;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.remote.model.ListaReferenciaApiResponse;
import ec.pure.naportec.eir.data.remote.model.ReferenciaApiResponse;
import ec.pure.naportec.eir.data.resource.NetworkBoundResource;
import ec.pure.naportec.eir.data.resource.Resource;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class ReferenciaRepository { // el ParamciaRepository media entre la base de datos y el Remote Data Source

    private ReferenciaDao referenciaDao;
    private ApiServiceDole apiService;

    public ReferenciaRepository(ReferenciaDao referenciaDao, ApiServiceDole apiServiceDole) {
        this.referenciaDao = referenciaDao;
        this.apiService = apiServiceDole;
    }

    public Observable<Resource<List<ReferenciaEntity>>> cargarTablaReferencia() {
        return new NetworkBoundResource<List<ReferenciaEntity>, ListaReferenciaApiResponse>() {

            @Override
            protected boolean shouldFetch() {

                return true;
            }

            @NonNull
            @Override
            protected Observable<Resource<ListaReferenciaApiResponse>> createCall() {

                return apiService.recuperarTodaLaListaReferencia()
                        .flatMap(lstReferenciaApiResponse -> Observable.just(lstReferenciaApiResponse == null
                                ? Resource.error("", new ListaReferenciaApiResponse())
                                : Resource.success(lstReferenciaApiResponse)));
            }

            @NonNull
            @Override
            protected Flowable<List<ReferenciaEntity>> loadFromDb() {

                List<ReferenciaEntity> referenciaEntityList = referenciaDao.obtenerTablaReferencia();
                if (referenciaEntityList == null || referenciaEntityList.isEmpty()) {

                    return Flowable.empty();
                }
                return Flowable.just(referenciaEntityList);
            }

            @Override// aqui se guarda los datos de api en la base local
            protected void saveCallResult(@NonNull ListaReferenciaApiResponse lstReferenciaApiResponse) {
//                System.out.println("aqui va saveCallResult cargarTablaReferencia " + lstReferenciaApiResponse);

                referenciaDao.borrarTablaReferencia();

                List<ReferenciaEntity> refEntityList = new ArrayList<>();

                for (ReferenciaApiResponse data : lstReferenciaApiResponse.getData()) {
                    ReferenciaEntity refEntity = new ReferenciaEntity();

                    refEntity.setGenrciUuid(data.getGenrciUuid());
                    refEntity.setGentrfUuid(data.getGentrfUuid());
                    refEntity.setGenrciCodigo(data.getGenrciCodigo());
                    refEntity.setGenrciDescripcion(data.getGenrciDescripcion());
                    refEntity.setGenrciDatovar1(data.getGenrciDatovar1());
                    refEntity.setGenrciDatovar2(data.getGenrciDatovar2());
                    refEntity.setGenrciDatovar3(data.getGenrciDatovar3());
                    refEntity.setGenrciDatovar4(data.getGenrciDatovar4());
                    refEntity.setGenrciDatovar5(data.getGenrciDatovar5());
                    refEntity.setGenrciDatonum1(data.getGenrciDatonum1());
                    refEntity.setGenrciDatonum2(data.getGenrciDatonum2());
                    refEntity.setGenrciDatonum3(data.getGenrciDatonum3());
                    refEntity.setGenrciDatonum4(data.getGenrciDatonum4());
                    refEntity.setGenrciDatovar5(data.getGenrciDatovar5());
                    refEntity.setGenmodCodigo(data.getGenmodCodigo());
                    refEntity.setGentrfCodigo(data.getGentrfCodigo());

                    refEntityList.add(refEntity);
                }
                referenciaDao.insertarTodoReferencacia(refEntityList);
            }

        }.getAsObservable();
    }

    public Observable<Resource<List<ReferenciaEntity>>> insertarReferenciaEntity(String genrci_descripcion, String genmodCodigo, String gentrfCodigo, String orderBy, boolean isFetch) {
        return new NetworkBoundResource<List<ReferenciaEntity>, ReferenciaEntity>() {

            @Override
            protected boolean shouldFetch() {

                return isFetch;
            }

            @NonNull
            @Override
            protected Observable<Resource<ReferenciaEntity>> createCall() {

                return apiService.ponerDato(genrci_descripcion, genmodCodigo, gentrfCodigo)
                        .flatMap(referenciaApiResponse -> Observable.just(referenciaApiResponse == null
                                ? Resource.error("", new ReferenciaEntity())
                                : Resource.success(referenciaApiResponse)));
            }

            @NonNull
            @Override
            protected Flowable<List<ReferenciaEntity>> loadFromDb() {
                List<ReferenciaEntity> consultaModuloTipoRef = new ArrayList<>();
                if (orderBy.equals("DatoNum1"))
                    consultaModuloTipoRef = referenciaDao.obtenerListaReferenciasOrderByDatoNum1(genmodCodigo, gentrfCodigo);
                if (orderBy.equals("Description"))
                    consultaModuloTipoRef = referenciaDao.obtenerListaReferenciasOrderByDescription(genmodCodigo, gentrfCodigo);

                if (consultaModuloTipoRef == null) {
//                    System.out.println("Flowable.empty");
                    return Flowable.empty();
                } //else con esto comprobamos que si hay datos aqui
                //  System.out.println("Flowable.just(referenciaEntity) ");
                return Flowable.just(consultaModuloTipoRef);
            }

            @Override// aqui se guarda los datos de api en la base local
            protected void saveCallResult(@NonNull ReferenciaEntity referencia) {
//                System.out.println("aqui va saveCallResult insert insertarReferenciaEntity " + referencia);
                ReferenciaEntity referenciaEntity = referenciaDao.obtenerReferencia(referencia.getGenrciUuid());
                if (referenciaEntity == null) {
                    referenciaDao.insertarReferencia(referencia);
                }

            }

        }.getAsObservable();
    }

}


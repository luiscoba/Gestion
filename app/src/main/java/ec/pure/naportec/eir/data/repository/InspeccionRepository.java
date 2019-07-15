package ec.pure.naportec.eir.data.repository;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.RespuestaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.remote.model.InspeccionApiResponse;
import ec.pure.naportec.eir.data.resource.NetworkBoundResource;
import ec.pure.naportec.eir.data.resource.Resource;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class InspeccionRepository { // el ParamciaRepository media entre la base de datos y el Remote Data Source

    private InspeccionDao inspeccionDao;
    private ApiServiceDole apiService;

    public InspeccionRepository(InspeccionDao inspeccionDao, ApiServiceDole apiServiceDole) {
        this.inspeccionDao = inspeccionDao;
        this.apiService = apiServiceDole;
    }

    public Observable<Resource<RespuestaEntity>> bajarInspeccion(String eiricb_placa, int eiricb_tipoproc, InspeccionEntity entity, boolean isFetch) {
        return new NetworkBoundResource<RespuestaEntity, InspeccionApiResponse>() {

            @Override
            protected boolean shouldFetch() {
                // cuando se hace una consulta
                // lo primero que se hace es borrar RespuestaEntity
                // para que no presente la informacion anterior
                inspeccionDao.borrarRespuestaEntity();
                return isFetch;
            }

            @NonNull
            @Override
            protected Observable<Resource<InspeccionApiResponse>> createCall() {
                // cuando se hace una consulta
                // lo primero que se hace es borrar RespuestaEntity
                // para que no presente la informacion anterior
                inspeccionDao.borrarRespuestaEntity();
                return apiService.pedirBajarInspeccion(eiricb_placa, eiricb_tipoproc)
                        .flatMap(inspeccionApiResponse -> Observable.just(inspeccionApiResponse == null
                                ? Resource.error("error conexión!", new InspeccionApiResponse())
                                : Resource.success(inspeccionApiResponse)));
            }

            @NonNull
            @Override
            protected Flowable<RespuestaEntity> loadFromDb() {
                RespuestaEntity respuestaEntity = inspeccionDao.obtenerRespuestaEntity();
                if (respuestaEntity == null) {
                    System.out.println("aqui va Flowable.empty() InspeccionREPO");
                    // cuando se retorna vacio, el Observable no emite ningun valor
                    // es decir el observer no escucha nada, no llega ni error ni nada
                    return Flowable.empty();
                } else
                    System.out.println("No Flowable " + respuestaEntity);
                return Flowable.just(respuestaEntity);
            }

            @Override
            protected void saveCallResult(@NonNull InspeccionApiResponse inspeccionApiResponse) {

                inspeccionDao.borrarRespuestaEntity();

                RespuestaEntity respuestaDelServer = new RespuestaEntity();
                respuestaDelServer.setEstado(inspeccionApiResponse.getEstado());
                respuestaDelServer.setMensaje(inspeccionApiResponse.getMensaje());

                inspeccionDao.insertarRespuestaDelServidor(respuestaDelServer);

                inspeccionDao.borrarInspeccionEntity();
                if (inspeccionApiResponse.getEstado() == 1 || inspeccionApiResponse.getEstado() == 0) {
                    InspeccionEntity inspeccionEntity = inspeccionApiResponse.getData();
                    inspeccionDao.insertarInspeccionEntity(inspeccionEntity);
                }
            }
        }.getAsObservable();

    }

    public Observable<Resource<RespuestaEntity>> subirInspeccion(InspeccionEntity inspeccionEntity) {
        return new NetworkBoundResource<RespuestaEntity, InspeccionApiResponse>() {

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Observable<Resource<InspeccionApiResponse>> createCall() {
                return apiService.pedirSubirInspeccion(inspeccionEntity)
                        .flatMap(inspeccionApiResponse -> Observable.just(inspeccionApiResponse == null
                                ? Resource.error("error conexión!", new InspeccionApiResponse())
                                : Resource.success(inspeccionApiResponse)));
            }

            @NonNull
            @Override
            protected Flowable<RespuestaEntity> loadFromDb() {
                RespuestaEntity respuestaEntity = inspeccionDao.obtenerRespuestaEntity();
                if (respuestaEntity == null) {
                    System.out.println("aqui va Flowable.empty() InspeccionREPO");
                    // cuando se retorna vacio, el Observable no emite ningun valor
                    // es decir el observer no escucha nada, no llega ni error ni nada
                    return Flowable.empty();
                } else
                    System.out.println("No Flowable " + respuestaEntity);
                return Flowable.just(respuestaEntity);
            }

            @Override
            protected void saveCallResult(@NonNull InspeccionApiResponse inspeccionApiResponse) {
                inspeccionDao.borrarRespuestaEntity();

                RespuestaEntity respuestaDelServer = new RespuestaEntity();
                respuestaDelServer.setEstado(inspeccionApiResponse.getEstado());
                respuestaDelServer.setMensaje(inspeccionApiResponse.getMensaje());

                inspeccionDao.insertarRespuestaDelServidor(respuestaDelServer);
            }

        }.getAsObservable();
    }

}
package ec.pure.naportec.eir.data.repository;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import ec.pure.naportec.eir.data.resource.NetworkBoundResource;
import ec.pure.naportec.eir.data.resource.Resource;
import ec.pure.naportec.eir.data.local.dao.ParamciaDao;
import ec.pure.naportec.eir.data.local.entity.ParamciaEntity;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.remote.model.ListaParamciaApiResponse;
import ec.pure.naportec.eir.data.remote.model.ParamciaApiResponse;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;

@Singleton
public class ParamciaRepository { // el ParamciaRepository media entre la base de datos y el Remote Data Source

    private ParamciaDao paramciaDao;
    private ApiServiceDole apiService;

    public ParamciaRepository(ParamciaDao paramciaDao, ApiServiceDole apiServiceDole) {
        this.paramciaDao = paramciaDao;
        this.apiService = apiServiceDole;
    }

    public Observable<Resource<List<ParamciaEntity>>> cargarParamciaEntity() {
        return new NetworkBoundResource<List<ParamciaEntity>, ListaParamciaApiResponse>() {

            @Override
            protected boolean shouldFetch() {

                System.out.println("aqui va shouldFetch");
                return true;
            }

            @NonNull
            @Override
            protected Observable<Resource<ListaParamciaApiResponse>> createCall() {
                System.out.println("aqui va createCall Param");
                return apiService.recuperarTodaLaListaParamcia()
                       .flatMap(lstParamciaApiResponse -> Observable.just(lstParamciaApiResponse == null
                                ? Resource.error("aqui va error luis", new ListaParamciaApiResponse())
                                : Resource.success(lstParamciaApiResponse)));
            }

            @NonNull
            @Override// aqui accedemos a la tabla de la base de datos
            protected Flowable<List<ParamciaEntity>> loadFromDb() {
                System.out.println("aqui va loadFromDb Param ");
                List<ParamciaEntity> paramciaEntityList = paramciaDao.obtenerTodoParamcia();
                if (paramciaEntityList == null || paramciaEntityList.isEmpty()) {
                    System.out.println("aqui va Flowable.empty() Param ");
                    return Flowable.empty();
                }
                return Flowable.just(paramciaEntityList);
            }

            @Override// aqui se guarda los datos de api en la base local
            protected void saveCallResult(@NonNull ListaParamciaApiResponse lstParamciaApiResponse) {
                System.out.println("aqui va saveCallResult param " + lstParamciaApiResponse);
                List<ParamciaEntity> paramEntityList = new ArrayList<>();

                for (ParamciaApiResponse data : lstParamciaApiResponse.getData()) {
                    ParamciaEntity paramEntity = new ParamciaEntity();

                    paramEntity.setGenpciUuid(data.getGenpciUuid());
                    paramEntity.setGenmodCodigo(data.getGenmodCodigo());
                    paramEntity.setGenpciGrupo(data.getGenpciGrupo());
                    paramEntity.setGenpciClave(data.getGenpciClave());
                    paramEntity.setGenpciValor(data.getGenpciValor());

                    paramEntityList.add(paramEntity);
                }
                System.out.println("paramEntityList.size() " + paramEntityList.size());

                paramciaDao.insertarTodoParamcia(paramEntityList);

                System.out.println("otra vez paramEntityList.size() " + paramEntityList.size());
            }

        }.getAsObservable();

    }
}


package ec.pure.naportec.eir.data.remote.api;

import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.InspeccionApiResponse;
import ec.pure.naportec.eir.data.remote.model.LoginApiResponse;
import ec.pure.naportec.eir.data.remote.model.ListaParamciaApiResponse;
import ec.pure.naportec.eir.data.remote.model.ListaReferenciaApiResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServiceDole {

    @GET("v1/dwReferenciacia?")
    Observable<ListaReferenciaApiResponse> recuperarTodaLaListaReferencia();

    @GET("v1/dwParamcia?")
    Observable<ListaParamciaApiResponse> recuperarTodaLaListaParamcia();

    @POST("v1/upReferenciacia?")
    Observable<ReferenciaEntity> ponerDato(@Query("genrci_descripcion") String genrci_descripcion, @Query("modulo") String modulo, @Query("tiporef") String tiporef);

    @POST("v1/login?")
    Call<LoginApiResponse> login(@Query("username") String type, @Query("password") String password);

    @POST("v1/dwConsulPlaca?")
    Observable<InspeccionApiResponse> pedirBajarInspeccion(@Query("iprec_placa") String iprec_placa, @Query("tipo") int tipo);

    @POST("v1/upinspeccion?")
    Observable<InspeccionApiResponse> pedirSubirInspeccion(@Body InspeccionEntity inspeccionEntity);

}

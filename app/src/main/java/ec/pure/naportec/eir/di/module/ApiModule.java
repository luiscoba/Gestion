package ec.pure.naportec.eir.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.data.remote.interceptor.NetworkInterceptor;
import ec.pure.naportec.eir.ui.drawernavigator.sesion.LoginActivity;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ec.pure.naportec.eir.AppConstants.ROOT_API_URL;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Cache provideCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }


    @Provides
    @Singleton
    NetworkInterceptor provideNetworkInterceptor(Application application) {
        return new NetworkInterceptor(application.getApplicationContext());
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache, NetworkInterceptor networkInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();//logging nos permitirá ver en el logcat, los datos que se envían y se reciben del servidor(nos informa si esta correcto o hay errores)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(cache);
        httpClient.addInterceptor(networkInterceptor);
        httpClient.addInterceptor(logging);
// aqui bloqueamos la intercepcion del OAuth        httpClient.addNetworkInterceptor(new RequestInterceptor());
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))//addConverterFactory nos permite recibir el JSON en la clase java(lo transforma con el GsonConverterFactory.create), los datos del servidor y nos lo deja
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Agregue una fábrica de adaptadores de llamada para admitir tipos de devolución de métodos de servicio distintos de la llamada
                .baseUrl(ROOT_API_URL) // le indicamos la raíz del dominio a Retrofit
                .client(okHttpClient)// vamos a ver las peticiones que se reciben del cliente okHttpClient
                .build();
    }

    @Provides
    @Singleton
    ApiServiceDole provideApiServiceDole(Retrofit retrofit) {
        return retrofit.create(ApiServiceDole.class);
    }

}

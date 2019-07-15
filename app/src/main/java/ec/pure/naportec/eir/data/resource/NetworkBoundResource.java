package ec.pure.naportec.eir.data.resource;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import ec.pure.naportec.eir.AppController;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* NetworkBoundResource es una clase genérica,
 * que puede proporcionar un recurso respaldado tanto por la base de datos sqlite como por la red.
 * NetworkBoundResource convierte un valor 'RequestType.' a otro valor 'ResultType'
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Observable<Resource<ResultType>> result;

    @MainThread
    protected NetworkBoundResource() {
        Observable<Resource<ResultType>> source;
        if (shouldFetch()) {
            source = createCall()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(apiResponse -> saveCallResult(processResponse(apiResponse)))
                    .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                    .doOnError(this::onFetchFailed)
                    .onErrorResumeNext(t -> {
                        return loadFromDb()
                                .toObservable()
                                .map(data -> Resource.error(t.getMessage(), data));

                    })
                    .observeOn(AndroidSchedulers.mainThread()); // el resultado volverá al hilo principal
        } else {
            source = loadFromDb()
                    .toObservable()
                    .map(Resource::success);
        }

        result = Observable.concat( // devuelve un Observable que emite los elementos emitidos por dos ObservableSources, uno tras otro, sin intercalarlos
                loadFromDb()
                        .toObservable()
                        .map(Resource::loading)
                        .take(1), // devuelve el primero de los items emitidos
                source
        );
    }

    public Observable<Resource<ResultType>> getAsObservable() {
        return result;
    }

    protected void onFetchFailed(Throwable t) {// en caso de que haya un error de conexion con la API
                Log.e("Error de conexión ", t.toString());
    }

    @WorkerThread
    protected RequestType processResponse(Resource<RequestType> response) {
        return response.data;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch();

    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Observable<Resource<RequestType>> createCall();
}
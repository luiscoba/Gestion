package ec.pure.naportec.eir.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ec.pure.naportec.eir.data.local.AppDatabase;
import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.dao.ParamciaDao;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;

import static ec.pure.naportec.eir.AppConstants.ROOM_DATABASE;

// el modulo se encarga de proveer a nuestra actividad todas las instancias necesarias, para que funcione nuestras clases
// debemos crear los modulos para que dagger sepa como devolver los objetos que serán requeridos
@Module
public class DbModule {
    // Los Provides van delante de los métodos que proporcionan objetos para la inyección de dependencias
    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, ROOM_DATABASE)
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    ReferenciaDao provideReferenciaDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.referenciaDao();
    }

    @Provides
    @Singleton
    ParamciaDao provideParamciaDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.paramciaDao();
    }

    @Provides
    @Singleton
    InspeccionDao provideInspeccionDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.inspeccionDao();
    }
}



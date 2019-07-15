package ec.pure.naportec.eir;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ec.pure.naportec.eir.di.component.DaggerApiComponent;
import ec.pure.naportec.eir.di.module.ApiModule;
import ec.pure.naportec.eir.di.module.DbModule;

//siempre que se extiende de Application se lo declara con name en el manifest
public class AppController extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //a partir de la palabrar Dagger va el nombre de la interfaz que tiene los componentes
        //entonces Dagger generará en este caso 'DaggerApiComponent'
        DaggerApiComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .dbModule(new DbModule())
                .build()
                .inject(this);
    }
}
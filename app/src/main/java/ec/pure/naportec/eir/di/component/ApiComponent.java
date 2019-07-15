package ec.pure.naportec.eir.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import ec.pure.naportec.eir.AppController;
import ec.pure.naportec.eir.di.module.ActivityModule;
import ec.pure.naportec.eir.di.module.ApiModule;
import ec.pure.naportec.eir.di.module.DbModule;
import ec.pure.naportec.eir.di.module.FragmentModule;
import ec.pure.naportec.eir.di.module.ViewModelModule;

@Singleton
// el componente es el puente entre los módulos creados y la parte del código que va a solicitar
// esos objetos para hacer la inyección de dependencias
@Component(modules = {  ApiModule.class,
                        DbModule.class,
                        ViewModelModule.class,
                        AndroidSupportInjectionModule.class,
                        ActivityModule.class,
                        FragmentModule.class})
public interface ApiComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        ApiComponent build();
    }

    void inject(AppController appController);
}

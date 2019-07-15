package ec.pure.naportec.eir.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ec.pure.naportec.eir.data.local.converter.ChassisListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.ContenedorListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.ImagenListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.LlantaListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.QrListTypeConverter;
import ec.pure.naportec.eir.data.local.dao.InspeccionDao;
import ec.pure.naportec.eir.data.local.dao.ParamciaDao;
import ec.pure.naportec.eir.data.local.dao.ReferenciaDao;
import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.ParamciaEntity;
import ec.pure.naportec.eir.data.local.entity.RespuestaEntity;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;

import static ec.pure.naportec.eir.AppConstants.ROOM_DATABASE;

@Database(entities = {
        RespuestaEntity.class,
        InspeccionEntity.class,
        ReferenciaEntity.class,
        ParamciaEntity.class},
        version = 1,
        exportSchema = false)
@TypeConverters({QrListTypeConverter.class,
        ContenedorListTypeConverter.class,
        LlantaListTypeConverter.class,
        ImagenListTypeConverter.class,
        ChassisListTypeConverter.class})//para cargar listas de datos en los Entities
public abstract class AppDatabase extends RoomDatabase {

    public abstract ReferenciaDao referenciaDao(); // esto le permite integrarse con Dagger

    public abstract ParamciaDao paramciaDao();

    public abstract InspeccionDao inspeccionDao();

    // volatile garantiza que el valor de INSTANCE se escribe de nuevo desde la memoria caché de la CPU a la memoria principal.
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, ROOM_DATABASE)
                .allowMainThreadQueries().build();
    }

}


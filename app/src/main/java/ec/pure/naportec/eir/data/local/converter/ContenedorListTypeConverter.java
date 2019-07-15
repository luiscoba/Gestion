package ec.pure.naportec.eir.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;

// las clases converter se encargaran de convertir desde el JSON en una list<Cast>
public class ContenedorListTypeConverter {

    @TypeConverter
    public List<ContenedorInspeccion> fromString(String value) {
        Type listType = new TypeToken<List<ContenedorInspeccion>>() {
        }.getType();
        List<ContenedorInspeccion> contenedores = new Gson().fromJson(value, listType);
        return contenedores;
    }

    @TypeConverter
    public String fromList(List<ContenedorInspeccion> contenedores) {
        return new Gson().toJson(contenedores);
    }
}

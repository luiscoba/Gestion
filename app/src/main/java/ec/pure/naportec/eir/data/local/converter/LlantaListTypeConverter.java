package ec.pure.naportec.eir.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ec.pure.naportec.eir.data.remote.model.LlantaInspeccion;

// las clases converter se encargaran de convertir desde el JSON en una list<Cast>
public class LlantaListTypeConverter {

    @TypeConverter
    public List<LlantaInspeccion> fromString(String value) {
        Type listType = new TypeToken<List<LlantaInspeccion>>() {
        }.getType();
        List<LlantaInspeccion> llantas = new Gson().fromJson(value, listType);
        return llantas;
    }

    @TypeConverter
    public String fromList(List<LlantaInspeccion> llantas) {
        return new Gson().toJson(llantas);
    }
}

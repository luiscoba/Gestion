package ec.pure.naportec.eir.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.data.remote.model.ImagenInspeccion;

// las clases converter se encargaran de convertir desde el JSON en una list<Cast>
public class ImagenListTypeConverter {

    @TypeConverter
    public List<ImagenInspeccion> fromString(String value) {
        Type listType = new TypeToken<List<ImagenInspeccion>>() {
        }.getType();
        List<ImagenInspeccion> imagen = new Gson().fromJson(value, listType);
        return imagen;
    }

    @TypeConverter
    public String fromList(List<ImagenInspeccion> imagen) {
        return new Gson().toJson(imagen);
    }
}

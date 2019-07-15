package ec.pure.naportec.eir.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.data.remote.model.LlantaInspeccion;

// las clases converter se encargaran de convertir desde el JSON en una list<Cast>
public class ChassisListTypeConverter {

    @TypeConverter
    public List<ChassisInspeccion> fromString(String value) {
        Type listType = new TypeToken<List<ChassisInspeccion>>() {
        }.getType();
        List<ChassisInspeccion> chassis = new Gson().fromJson(value, listType);
        return chassis;
    }

    @TypeConverter
    public String fromList(List<ChassisInspeccion> chassis) {
        return new Gson().toJson(chassis);
    }
}

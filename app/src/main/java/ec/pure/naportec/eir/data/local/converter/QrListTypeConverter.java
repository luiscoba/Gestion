package ec.pure.naportec.eir.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.data.remote.model.QrInspeccion;

// las clases converter se encargaran de convertir desde el JSON en una list<Cast>
public class QrListTypeConverter {

    @TypeConverter
    public List<QrInspeccion> fromString(String value) {
        Type listType = new TypeToken<List<QrInspeccion>>() {
        }.getType();
        List<QrInspeccion> qr = new Gson().fromJson(value, listType);
        return qr;
    }

    @TypeConverter
    public String fromList(List<QrInspeccion> qr) {
        return new Gson().toJson(qr);
    }
}

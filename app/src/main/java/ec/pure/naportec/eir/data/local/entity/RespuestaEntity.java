package ec.pure.naportec.eir.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "RespuestaEntity")
public class RespuestaEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("estado")
    @Expose
    private Integer estado;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeValue(this.estado);
        dest.writeString(this.mensaje);
    }

    public RespuestaEntity() {
    }

    protected RespuestaEntity(Parcel in) {
        this.id = in.readInt();
        this.estado = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mensaje = in.readString();
    }

    public static final Creator<RespuestaEntity> CREATOR = new Creator<RespuestaEntity>() {
        @Override
        public RespuestaEntity createFromParcel(Parcel source) {
            return new RespuestaEntity(source);
        }

        @Override
        public RespuestaEntity[] newArray(int size) {
            return new RespuestaEntity[size];
        }
    };

}

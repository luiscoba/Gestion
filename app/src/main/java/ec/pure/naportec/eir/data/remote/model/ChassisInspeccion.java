package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChassisInspeccion implements Parcelable{

    @SerializedName("eiridh_estado")
    @Expose
    private Boolean eiridhEstado;
    @SerializedName("genrci_uuid")
    @Expose
    private String genrciUuid;

    public Boolean getEiridhEstado() {
        return eiridhEstado;
    }

    public void setEiridhEstado(Boolean eiridhEstado) {
        this.eiridhEstado = eiridhEstado;
    }

    public String getGenrciUuid() {
        return genrciUuid;
    }

    public void setGenrciUuid(String genrciUuid) {
        this.genrciUuid = genrciUuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.eiridhEstado);
        dest.writeString(this.genrciUuid);
    }

    public ChassisInspeccion() {
    }

    protected ChassisInspeccion(Parcel in) {
        this.eiridhEstado = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.genrciUuid = in.readString();
    }

    public static final Creator<ChassisInspeccion> CREATOR = new Creator<ChassisInspeccion>() {
        @Override
        public ChassisInspeccion createFromParcel(Parcel source) {
            return new ChassisInspeccion(source);
        }

        @Override
        public ChassisInspeccion[] newArray(int size) {
            return new ChassisInspeccion[size];
        }
    };
}


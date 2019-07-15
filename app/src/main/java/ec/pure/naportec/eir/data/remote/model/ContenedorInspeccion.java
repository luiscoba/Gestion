package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorInspeccion implements Parcelable{

    @SerializedName("eiridc_cara")
    @Expose
    private String eiridcCara;
    @SerializedName("eiridc_posic")
    @Expose
    private String eiridcPosic;
    @SerializedName("genrci_uuid")
    @Expose
    private String genrciUuid;

    @SerializedName("daniado")
    @Expose
    private List<Daniado> daniado = null;

    public String getEiridcCara() {
        return eiridcCara;
    }

    public void setEiridcCara(String eiridcCara) {
        this.eiridcCara = eiridcCara;
    }

    public String getEiridcPosic() {
        return eiridcPosic;
    }

    public void setEiridcPosic(String eiridcPosic) {
        this.eiridcPosic = eiridcPosic;
    }

    public String getGenrciUuid() {
        return genrciUuid;
    }

    public void setGenrciUuid(String genrciUuid) {
        this.genrciUuid = genrciUuid;
    }

    public List<Daniado> getDaniado() {
        return daniado;
    }

    public void setDaniado(List<Daniado> daniado) {
        this.daniado = daniado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eiridcCara);
        dest.writeString(this.eiridcPosic);
        dest.writeString(this.genrciUuid);
        dest.writeTypedList(this.daniado);
    }

    public ContenedorInspeccion() {
    }

    protected ContenedorInspeccion(Parcel in) {
        this.eiridcCara = in.readString();
        this.eiridcPosic = in.readString();
        this.genrciUuid = in.readString();
        this.daniado = in.createTypedArrayList(Daniado.CREATOR);
    }

    public static final Creator<ContenedorInspeccion> CREATOR = new Creator<ContenedorInspeccion>() {
        @Override
        public ContenedorInspeccion createFromParcel(Parcel source) {
            return new ContenedorInspeccion(source);
        }

        @Override
        public ContenedorInspeccion[] newArray(int size) {
            return new ContenedorInspeccion[size];
        }
    };
}


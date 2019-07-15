package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagenInspeccion implements Parcelable {

    @SerializedName("genimg_tipo")
    @Expose
    private String genimgTipo;
    @SerializedName("genimg_tipo_uuid")
    @Expose
    private String genimgTipoUuid;
    @SerializedName("genimg_secuencia")
    @Expose
    private String genimgSecuencia;
    @SerializedName("genimg_nombre")
    @Expose
    private String genimgNombre;
    @SerializedName("genimg_comentario")
    @Expose
    private String genimgComentario;

    public String getGenimgTipo() {
        return genimgTipo;
    }

    public void setGenimgTipo(String genimgTipo) {
        this.genimgTipo = genimgTipo;
    }

    public String getGenimgTipoUuid() {
        return genimgTipoUuid;
    }

    public void setGenimgTipoUuid(String genimgTipoUuid) {
        this.genimgTipoUuid = genimgTipoUuid;
    }

    public String getGenimgSecuencia() {
        return genimgSecuencia;
    }

    public void setGenimgSecuencia(String genimgSecuencia) {
        this.genimgSecuencia = genimgSecuencia;
    }

    public String getGenimgNombre() {
        return genimgNombre;
    }

    public void setGenimgNombre(String genimgNombre) {
        this.genimgNombre = genimgNombre;
    }

    public String getGenimgComentario() {
        return genimgComentario;
    }

    public void setGenimgComentario(String genimgComentario) {
        this.genimgComentario = genimgComentario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.genimgTipo);
        dest.writeString(this.genimgTipoUuid);
        dest.writeString(this.genimgSecuencia);
        dest.writeString(this.genimgNombre);
        dest.writeString(this.genimgComentario);
    }

    public ImagenInspeccion() {
    }

    protected ImagenInspeccion(Parcel in) {
        this.genimgTipo = in.readString();
        this.genimgTipoUuid = in.readString();
        this.genimgSecuencia = in.readString();
        this.genimgNombre = in.readString();
        this.genimgComentario = in.readString();
    }

    public static final Creator<ImagenInspeccion> CREATOR = new Creator<ImagenInspeccion>() {
        @Override
        public ImagenInspeccion createFromParcel(Parcel source) {
            return new ImagenInspeccion(source);
        }

        @Override
        public ImagenInspeccion[] newArray(int size) {
            return new ImagenInspeccion[size];
        }
    };
}

package ec.pure.naportec.eir.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = ("genpciUuid"))
public class ParamciaEntity  implements Parcelable {

    @SerializedName("genpci_uuid")
    @Expose
    @NonNull
    private String genpciUuid;
    @SerializedName("genmod_codigo")
    @Expose
    private String genmodCodigo;
    @SerializedName("genpci_grupo")
    @Expose
    private String genpciGrupo;
    @SerializedName("genpci_clave")
    @Expose
    private String genpciClave;
    @SerializedName("genpci_valor")
    @Expose
    private String genpciValor;

    public String getGenpciUuid() {
        return genpciUuid;
    }

    public void setGenpciUuid(String genpciUuid) {
        this.genpciUuid = genpciUuid;
    }

    public String getGenmodCodigo() {
        return genmodCodigo;
    }

    public void setGenmodCodigo(String genmodCodigo) {
        this.genmodCodigo = genmodCodigo;
    }

    public String getGenpciGrupo() {
        return genpciGrupo;
    }

    public void setGenpciGrupo(String genpciGrupo) {
        this.genpciGrupo = genpciGrupo;
    }

    public String getGenpciClave() {
        return genpciClave;
    }

    public void setGenpciClave(String genpciClave) {
        this.genpciClave = genpciClave;
    }

    public String getGenpciValor() {
        return genpciValor;
    }

    public void setGenpciValor(String genpciValor) {
        this.genpciValor = genpciValor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.genpciUuid);
        dest.writeString(this.genmodCodigo);
        dest.writeString(this.genpciGrupo);
        dest.writeString(this.genpciClave);
        dest.writeString(this.genpciValor);
    }

    public ParamciaEntity() {
    }

    protected ParamciaEntity(Parcel in) {
        this.genpciUuid = in.readString();
        this.genmodCodigo = in.readString();
        this.genpciGrupo = in.readString();
        this.genpciClave = in.readString();
        this.genpciValor = in.readString();
    }

    public static final Creator<ParamciaEntity> CREATOR = new Creator<ParamciaEntity>() {
        @Override
        public ParamciaEntity createFromParcel(Parcel source) {
            return new ParamciaEntity(source);
        }

        @Override
        public ParamciaEntity[] newArray(int size) {
            return new ParamciaEntity[size];
        }
    };
}
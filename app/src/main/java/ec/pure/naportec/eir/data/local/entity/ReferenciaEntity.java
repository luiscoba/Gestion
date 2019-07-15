package ec.pure.naportec.eir.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = ("genrciUuid"))
public class ReferenciaEntity implements Parcelable {

    @SerializedName("genrci_uuid")
    @NonNull
    @Expose
    private String genrciUuid;
    @SerializedName("gentrf_uuid")
    @Expose
    private String gentrfUuid;
    @SerializedName("genrci_codigo")
    @Expose
    private String genrciCodigo;
    @SerializedName("genrci_descripcion")
    @Expose
    private String genrciDescripcion;
    @SerializedName("genrci_datovar1")
    @Expose
    private String genrciDatovar1;
    @SerializedName("genrci_datovar2")
    @Expose
    private String genrciDatovar2;
    @SerializedName("genrci_datovar3")
    @Expose
    private String genrciDatovar3;
    @SerializedName("genrci_datovar4")
    @Expose
    private String genrciDatovar4;
    @SerializedName("genrci_datovar5")
    @Expose
    private String genrciDatovar5;
    @SerializedName("genrci_datonum1")
    @Expose
    private String genrciDatonum1;
    @SerializedName("genrci_datonum2")
    @Expose
    private String genrciDatonum2;
    @SerializedName("genrci_datonum3")
    @Expose
    private String genrciDatonum3;
    @SerializedName("genrci_datonum4")
    @Expose
    private String genrciDatonum4;
    @SerializedName("genrci_datonum5")
    @Expose
    private String genrciDatonum5;
    @SerializedName("genmod_codigo")
    @Expose
    private String genmodCodigo;
    @SerializedName("gentrf_codigo")
    @Expose
    private String gentrfCodigo;

    public String getGenrciUuid() {
        return genrciUuid;
    }

    public void setGenrciUuid(String genrciUuid) {
        this.genrciUuid = genrciUuid;
    }

    public String getGentrfUuid() {
        return gentrfUuid;
    }

    public void setGentrfUuid(String gentrfUuid) {
        this.gentrfUuid = gentrfUuid;
    }

    public String getGenrciCodigo() {
        return genrciCodigo;
    }

    public void setGenrciCodigo(String genrciCodigo) {
        this.genrciCodigo = genrciCodigo;
    }

    public String getGenrciDescripcion() {
        return genrciDescripcion;
    }

    public void setGenrciDescripcion(String genrciDescripcion) {
        this.genrciDescripcion = genrciDescripcion;
    }

    public String getGenrciDatovar1() {
        return genrciDatovar1;
    }

    public void setGenrciDatovar1(String genrciDatovar1) {
        this.genrciDatovar1 = genrciDatovar1;
    }

    public String getGenrciDatovar2() {
        return genrciDatovar2;
    }

    public void setGenrciDatovar2(String genrciDatovar2) {
        this.genrciDatovar2 = genrciDatovar2;
    }

    public String getGenrciDatovar3() {
        return genrciDatovar3;
    }

    public void setGenrciDatovar3(String genrciDatovar3) {
        this.genrciDatovar3 = genrciDatovar3;
    }

    public String getGenrciDatovar4() {
        return genrciDatovar4;
    }

    public void setGenrciDatovar4(String genrciDatovar4) {
        this.genrciDatovar4 = genrciDatovar4;
    }

    public String getGenrciDatovar5() {
        return genrciDatovar5;
    }

    public void setGenrciDatovar5(String genrciDatovar5) {
        this.genrciDatovar5 = genrciDatovar5;
    }

    public String getGenrciDatonum1() {
        return genrciDatonum1;
    }

    public void setGenrciDatonum1(String genrciDatonum1) {
        this.genrciDatonum1 = genrciDatonum1;
    }

    public String getGenrciDatonum2() {
        return genrciDatonum2;
    }

    public void setGenrciDatonum2(String genrciDatonum2) {
        this.genrciDatonum2 = genrciDatonum2;
    }

    public String getGenrciDatonum3() {
        return genrciDatonum3;
    }

    public void setGenrciDatonum3(String genrciDatonum3) {
        this.genrciDatonum3 = genrciDatonum3;
    }

    public String getGenrciDatonum4() {
        return genrciDatonum4;
    }

    public void setGenrciDatonum4(String genrciDatonum4) {
        this.genrciDatonum4 = genrciDatonum4;
    }

    public String getGenrciDatonum5() {
        return genrciDatonum5;
    }

    public void setGenrciDatonum5(String genrciDatonum5) {
        this.genrciDatonum5 = genrciDatonum5;
    }

    public String getGenmodCodigo() {
        return genmodCodigo;
    }

    public void setGenmodCodigo(String genmodCodigo) {
        this.genmodCodigo = genmodCodigo;
    }

    public String getGentrfCodigo() {
        return gentrfCodigo;
    }

    public void setGentrfCodigo(String gentrfCodigo) {
        this.gentrfCodigo = gentrfCodigo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.genrciUuid);
        dest.writeString(this.gentrfUuid);
        dest.writeString(this.genrciCodigo);
        dest.writeString(this.genrciDescripcion);
        dest.writeString(this.genrciDatovar1);
        dest.writeString(this.genrciDatovar2);
        dest.writeString(this.genrciDatovar3);
        dest.writeString(this.genrciDatovar4);
        dest.writeString(this.genrciDatovar5);
        dest.writeString(this.genrciDatonum1);
        dest.writeString(this.genrciDatonum2);
        dest.writeString(this.genrciDatonum3);
        dest.writeString(this.genrciDatonum4);
        dest.writeString(this.genrciDatonum5);
        dest.writeString(this.genmodCodigo);
        dest.writeString(this.gentrfCodigo);
    }

    public ReferenciaEntity() {
    }

    protected ReferenciaEntity(Parcel in) {
        this.genrciUuid = in.readString();
        this.gentrfUuid = in.readString();
        this.genrciCodigo = in.readString();
        this.genrciDescripcion = in.readString();
        this.genrciDatovar1 = in.readString();
        this.genrciDatovar2 = in.readString();
        this.genrciDatovar3 = in.readString();
        this.genrciDatovar4 = in.readString();
        this.genrciDatovar5 = in.readString();
        this.genrciDatonum1 = in.readString();
        this.genrciDatonum2 = in.readString();
        this.genrciDatonum3 = in.readString();
        this.genrciDatonum4 = in.readString();
        this.genrciDatonum5 = in.readString();
        this.genmodCodigo = in.readString();
        this.gentrfCodigo = in.readString();
    }

    public static final Creator<ReferenciaEntity> CREATOR = new Creator<ReferenciaEntity>() {
        @Override
        public ReferenciaEntity createFromParcel(Parcel source) {
            return new ReferenciaEntity(source);
        }

        @Override
        public ReferenciaEntity[] newArray(int size) {
            return new ReferenciaEntity[size];
        }
    };
}

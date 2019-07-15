package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginApiResponse implements Parcelable {


    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("estado")
    @Expose
    private Integer estado;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mensaje);
        dest.writeValue(this.estado);
    }

    public LoginApiResponse() {
    }

    protected LoginApiResponse(Parcel in) {
        this.mensaje = in.readString();
        this.estado = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<LoginApiResponse> CREATOR = new Creator<LoginApiResponse>() {
        @Override
        public LoginApiResponse createFromParcel(Parcel source) {
            return new LoginApiResponse(source);
        }

        @Override
        public LoginApiResponse[] newArray(int size) {
            return new LoginApiResponse[size];
        }
    };
}
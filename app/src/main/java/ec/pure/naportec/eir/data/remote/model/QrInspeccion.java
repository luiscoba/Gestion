package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrInspeccion implements Parcelable {

    @SerializedName("eiridr_sello")
    @Expose
    private String eiridrSello;

    public String getEiridrSello() {
        return eiridrSello;
    }

    public void setEiridrSello(String eiridrSello) {
        this.eiridrSello = eiridrSello;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eiridrSello);
    }

    public QrInspeccion() {
    }

    protected QrInspeccion(Parcel in) {
        this.eiridrSello = in.readString();
    }

    public static final Creator<QrInspeccion> CREATOR = new Creator<QrInspeccion>() {
        @Override
        public QrInspeccion createFromParcel(Parcel source) {
            return new QrInspeccion(source);
        }

        @Override
        public QrInspeccion[] newArray(int size) {
            return new QrInspeccion[size];
        }
    };
}

package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Daniado implements Parcelable{

    @SerializedName("genrci_uuid")
    @Expose
    private String genrciUuid;

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
        dest.writeString(this.genrciUuid);
    }

    public Daniado() {
    }

    protected Daniado(Parcel in) {
        this.genrciUuid = in.readString();
    }

    public static final Creator<Daniado> CREATOR = new Creator<Daniado>() {
        @Override
        public Daniado createFromParcel(Parcel source) {
            return new Daniado(source);
        }

        @Override
        public Daniado[] newArray(int size) {
            return new Daniado[size];
        }
    };
}
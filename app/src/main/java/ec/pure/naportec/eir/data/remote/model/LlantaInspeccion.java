package ec.pure.naportec.eir.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LlantaInspeccion implements Parcelable{

    @SerializedName("genrci_uuid_1")
    @Expose
    private String genrciUuid1;
    @SerializedName("genrci_uuid_2")
    @Expose
    private String genrciUuid2;
    @SerializedName("genrci_uuid_3")
    @Expose
    private String genrciUuid3;

    public String getGenrciUuid1() {
        return genrciUuid1;
    }

    public void setGenrciUuid1(String genrciUuid1) {
        this.genrciUuid1 = genrciUuid1;
    }

    public String getGenrciUuid2() {
        return genrciUuid2;
    }

    public void setGenrciUuid2(String genrciUuid2) {
        this.genrciUuid2 = genrciUuid2;
    }

    public String getGenrciUuid3() {
        return genrciUuid3;
    }

    public void setGenrciUuid3(String genrciUuid3) {
        this.genrciUuid3 = genrciUuid3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.genrciUuid1);
        dest.writeString(this.genrciUuid2);
        dest.writeString(this.genrciUuid3);
    }

    public LlantaInspeccion() {
    }

    protected LlantaInspeccion(Parcel in) {
        this.genrciUuid1 = in.readString();
        this.genrciUuid2 = in.readString();
        this.genrciUuid3 = in.readString();
    }

    public static final Creator<LlantaInspeccion> CREATOR = new Creator<LlantaInspeccion>() {
        @Override
        public LlantaInspeccion createFromParcel(Parcel source) {
            return new LlantaInspeccion(source);
        }

        @Override
        public LlantaInspeccion[] newArray(int size) {
            return new LlantaInspeccion[size];
        }
    };
}


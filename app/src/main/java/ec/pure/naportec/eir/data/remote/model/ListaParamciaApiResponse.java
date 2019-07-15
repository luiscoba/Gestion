package ec.pure.naportec.eir.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListaParamciaApiResponse {

    @SerializedName("data")
    @Expose
    private List<ParamciaApiResponse> data;

    public ListaParamciaApiResponse() {
        this.data = data;
    }

    public List<ParamciaApiResponse> getData() {
        return data;
    }

    public void setData(List<ParamciaApiResponse> data) {
        this.data = data;
    }

}
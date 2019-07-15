package ec.pure.naportec.eir.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListaReferenciaApiResponse {

    public ListaReferenciaApiResponse() {
        this.data = new ArrayList<>();
    }

    @SerializedName("data")
    private List<ReferenciaApiResponse> data;

    public List<ReferenciaApiResponse> getData() {
        return data;
    }

    public void setData(List<ReferenciaApiResponse> data) {
        this.data = data;
    }
}

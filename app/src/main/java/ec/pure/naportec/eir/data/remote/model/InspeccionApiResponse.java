package ec.pure.naportec.eir.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;

public class InspeccionApiResponse {

    public InspeccionApiResponse() {
        this.data = new InspeccionEntity();
    }

    @SerializedName("estado")
    @Expose
    private Integer estado;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("data")
    @Expose
    private InspeccionEntity data;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public InspeccionEntity getData() {
        return data;
    }

    public void setData(InspeccionEntity data) {
        this.data = data;
    }
}

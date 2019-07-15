package ec.pure.naportec.eir.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaDeSubirEirApiResponse {
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

}

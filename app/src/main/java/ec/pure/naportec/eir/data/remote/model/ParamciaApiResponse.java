package ec.pure.naportec.eir.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParamciaApiResponse {

    @SerializedName("genpci_uuid")
    @Expose
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

}
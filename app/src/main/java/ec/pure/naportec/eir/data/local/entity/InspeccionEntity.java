package ec.pure.naportec.eir.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ec.pure.naportec.eir.data.local.converter.ChassisListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.ContenedorListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.ImagenListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.LlantaListTypeConverter;
import ec.pure.naportec.eir.data.local.converter.QrListTypeConverter;
import ec.pure.naportec.eir.data.remote.model.ChassisInspeccion;
import ec.pure.naportec.eir.data.remote.model.ContenedorInspeccion;
import ec.pure.naportec.eir.data.remote.model.ImagenInspeccion;
import ec.pure.naportec.eir.data.remote.model.LlantaInspeccion;
import ec.pure.naportec.eir.data.remote.model.QrInspeccion;

@Entity(primaryKeys = ("eiricbUuid"))
public class InspeccionEntity implements Parcelable {

    @SerializedName("eiricb_uuid")
    @Expose
    @NonNull
    private String eiricbUuid;
    @SerializedName("eiricb_codigo")
    @Expose
    private String eiricbCodigo;

    @SerializedName("eiricb_chasisproced")
    @Expose
    private String eiricbChasisproced;

    @SerializedName("eiricb_contlleno")
    @Expose
    private Boolean eiricbContlleno;

    @SerializedName("eiricb_contproced")
    @Expose
    private String eiricbContproced;

    @SerializedName("eiricb_tipoproc")
    @Expose
    private String eiricbTipoproc;
    @SerializedName("eiricb_idexterno")
    @Expose
    private String eiricbIdexterno;
    @SerializedName("eiricb_nrotransaccion")
    @Expose
    private String eiricbNrotransaccion;
    @SerializedName("eiricb_nroentrada")
    @Expose
    private String eiricbNroentrada;
    @SerializedName("eiricb_placa")
    @Expose
    private String eiricbPlaca;
    @SerializedName("eiricb_blbooking")
    @Expose
    private String eiricbBlbooking;
    @SerializedName("eiricb_buque")
    @Expose
    private String eiricbBuque;
    @SerializedName("eiricb_viaje")
    @Expose
    private String eiricbViaje;
    @SerializedName("eiricb_puerto")
    @Expose
    private String eiricbPuerto;
    @SerializedName("eiricb_chasis")
    @Expose
    private String eiricbChasis;
    @SerializedName("eiricb_genset")
    @Expose
    private String eiricbGenset;
    @SerializedName("eiricb_chofer_cedu")
    @Expose
    private String eiricbChoferCedu;
    @SerializedName("eiricb_chofer_name")
    @Expose
    private String eiricbChoferName;
    @SerializedName("eiricb_contenedor")
    @Expose
    private String eiricbContenedor;
    @SerializedName("genrci_uuid_2")
    @Expose
    private String genrciUuid2;
    @SerializedName("eiricb_peso")
    @Expose
    private String eiricbPeso;
    @SerializedName("eiricb_sellos")
    @Expose
    private String eiricbSellos;
    @SerializedName("eiricb_carga")
    @Expose
    private String eiricbCarga;
    @SerializedName("eiricb_tempera")
    @Expose
    private String eiricbTempera;
    @SerializedName("eiricb_ventilac")
    @Expose
    private String eiricbVentilac;
    @SerializedName("eiricb_transpor_code")
    @Expose
    private String eiricbTransporCode;
    @SerializedName("eiricb_transpor_name")
    @Expose
    private String eiricbTransporName;
    @SerializedName("eiricb_expimp_ruc")
    @Expose
    private String eiricbExpimpRuc;
    @SerializedName("eiricb_expimp_name")
    @Expose
    private String eiricbExpimpName;
    @SerializedName("eiricb_naviera")
    @Expose
    private String eiricbNaviera;
    @SerializedName("eiricb_maxgross")
    @Expose
    private String eiricbMaxgross;
    @SerializedName("eiricb_tare")
    @Expose
    private String eiricbTare;
    @SerializedName("eiricb_iso")
    @Expose
    private String eiricbIso;
    @SerializedName("eiricb_manufactured")
    @Expose
    private String eiricbManufactured;
    @SerializedName("eiricb_valor_hubo")
    @Expose
    private String eiricbValorHubo;
    @SerializedName("eiricb_valor_gene")
    @Expose
    private String eiricbValorGene;
    @SerializedName("eiricb_valor_comb")
    @Expose
    private String eiricbValorComb;
    @SerializedName("eiricb_observa1")
    @Expose
    private String eiricbObserva1;
    @SerializedName("eiricb_llt_sello_ief")
    @Expose
    private Boolean eiricbLltSelloIef;
    @SerializedName("eiricb_llt_sello_def")
    @Expose
    private Boolean eiricbLltSelloDef;
    @SerializedName("eiricb_llt_sello_iea")
    @Expose
    private Boolean eiricbLltSelloIea;
    @SerializedName("eiricb_llt_sello_dea")
    @Expose
    private Boolean eiricbLltSelloDea;
    @SerializedName("eiricb_llt_sello_tanque1")
    @Expose
    private String eiricbLltSelloTanque1;
    @SerializedName("eiricb_llt_sello_tanque2")
    @Expose
    private String eiricbLltSelloTanque2;
    @SerializedName("genrci_uuid_3")
    @Expose
    private String genrciUuid3;
    @SerializedName("eiricb_observa2")
    @Expose
    private String eiricbObserva2;
    @SerializedName("genrci_uuid_1")
    @Expose
    private String genrciUuid1;
    @SerializedName("eiricb_user")
    @Expose
    private String eiricbUser;
    @SerializedName("eiricb_ptc")
    @Expose
    private String eiricbPtc;
    @SerializedName("eiricb_userpatio")
    @Expose
    private String eiricbUserpatio;
    @SerializedName("eiricb_ptcpatio")
    @Expose
    private String eiricbPtcpatio;
    @SerializedName("eiricb_obspatio")
    @Expose
    private String eiricbObspatio;
    @SerializedName("eiricb_fechapatio")
    @Expose
    private String eiricbFechapatio;
    @SerializedName("eiricb_usertecnico")
    @Expose
    private String eiricbUsertecnico;
    @SerializedName("eiricb_ptctecnico")
    @Expose
    private String eiricbPtctecnico;
    @SerializedName("eiricb_obstecnico")
    @Expose
    private String eiricbObstecnico;
    @SerializedName("eiricb_fechatecnico")
    @Expose
    private String eiricbFechatecnico;
    @SerializedName("eiricb_usertaller")
    @Expose
    private String eiricbUsertaller;
    @SerializedName("eiricb_ptctaller")
    @Expose
    private String eiricbPtctaller;
    @SerializedName("eiricb_obstaller")
    @Expose
    private String eiricbObstaller;
    @SerializedName("eiricb_fechataller")
    @Expose
    private String eiricbFechataller;
    @SerializedName("eiricb_sync")
    @Expose
    private String eiricbSync;

    @SerializedName("eiricb_created_at")
    @Expose
    private String eiricbCreatedAt;

    @SerializedName("contenedor")
    @Expose
    @TypeConverters(ContenedorListTypeConverter.class)
    private List<ContenedorInspeccion> contenedor;
    @SerializedName("chasis")
    @Expose
    @TypeConverters(ChassisListTypeConverter.class)
    private List<ChassisInspeccion> chasis;
    @SerializedName("llanta")
    @Expose
    @TypeConverters(LlantaListTypeConverter.class)
    private List<LlantaInspeccion> llanta;

    @SerializedName("qr")
    @Expose
    @TypeConverters(QrListTypeConverter.class)
    private List<QrInspeccion> qr;
    @SerializedName("imagen")
    @Expose
    @TypeConverters(ImagenListTypeConverter.class)
    private List<ImagenInspeccion> imagen;

    @NonNull
    public String getEiricbUuid() {
        return eiricbUuid;
    }

    public void setEiricbUuid(@NonNull String eiricbUuid) {
        this.eiricbUuid = eiricbUuid;
    }

    public String getEiricbCodigo() {
        return eiricbCodigo;
    }

    public void setEiricbCodigo(String eiricbCodigo) {
        this.eiricbCodigo = eiricbCodigo;
    }

    public String getEiricbChasisproced() {
        return eiricbChasisproced;
    }

    public void setEiricbChasisproced(String eiricbChasisproced) {
        this.eiricbChasisproced = eiricbChasisproced;
    }

    public Boolean getEiricbContlleno() {
        return eiricbContlleno;
    }

    public void setEiricbContlleno(Boolean eiricbContlleno) {
        this.eiricbContlleno = eiricbContlleno;
    }

    public String getEiricbContproced() {
        return eiricbContproced;
    }

    public void setEiricbContproced(String eiricbContproced) {
        this.eiricbContproced = eiricbContproced;
    }

    public String getEiricbTipoproc() {
        return eiricbTipoproc;
    }

    public void setEiricbTipoproc(String eiricbTipoproc) {
        this.eiricbTipoproc = eiricbTipoproc;
    }

    public String getEiricbIdexterno() {
        return eiricbIdexterno;
    }

    public void setEiricbIdexterno(String eiricbIdexterno) {
        this.eiricbIdexterno = eiricbIdexterno;
    }

    public String getEiricbNrotransaccion() {
        return eiricbNrotransaccion;
    }

    public void setEiricbNrotransaccion(String eiricbNrotransaccion) {
        this.eiricbNrotransaccion = eiricbNrotransaccion;
    }

    public String getEiricbNroentrada() {
        return eiricbNroentrada;
    }

    public void setEiricbNroentrada(String eiricbNroentrada) {
        this.eiricbNroentrada = eiricbNroentrada;
    }

    public String getEiricbPlaca() {
        return eiricbPlaca;
    }

    public void setEiricbPlaca(String eiricbPlaca) {
        this.eiricbPlaca = eiricbPlaca;
    }

    public String getEiricbBlbooking() {
        return eiricbBlbooking;
    }

    public void setEiricbBlbooking(String eiricbBlbooking) {
        this.eiricbBlbooking = eiricbBlbooking;
    }

    public String getEiricbBuque() {
        return eiricbBuque;
    }

    public void setEiricbBuque(String eiricbBuque) {
        this.eiricbBuque = eiricbBuque;
    }

    public String getEiricbViaje() {
        return eiricbViaje;
    }

    public void setEiricbViaje(String eiricbViaje) {
        this.eiricbViaje = eiricbViaje;
    }

    public String getEiricbPuerto() {
        return eiricbPuerto;
    }

    public void setEiricbPuerto(String eiricbPuerto) {
        this.eiricbPuerto = eiricbPuerto;
    }

    public String getEiricbChasis() {
        return eiricbChasis;
    }

    public void setEiricbChasis(String eiricbChasis) {
        this.eiricbChasis = eiricbChasis;
    }

    public String getEiricbGenset() {
        return eiricbGenset;
    }

    public void setEiricbGenset(String eiricbGenset) {
        this.eiricbGenset = eiricbGenset;
    }

    public String getEiricbChoferCedu() {
        return eiricbChoferCedu;
    }

    public void setEiricbChoferCedu(String eiricbChoferCedu) {
        this.eiricbChoferCedu = eiricbChoferCedu;
    }

    public String getEiricbChoferName() {
        return eiricbChoferName;
    }

    public void setEiricbChoferName(String eiricbChoferName) {
        this.eiricbChoferName = eiricbChoferName;
    }

    public String getEiricbContenedor() {
        return eiricbContenedor;
    }

    public void setEiricbContenedor(String eiricbContenedor) {
        this.eiricbContenedor = eiricbContenedor;
    }

    public String getGenrciUuid2() {
        return genrciUuid2;
    }

    public void setGenrciUuid2(String genrciUuid2) {
        this.genrciUuid2 = genrciUuid2;
    }

    public String getEiricbPeso() {
        return eiricbPeso;
    }

    public void setEiricbPeso(String eiricbPeso) {
        this.eiricbPeso = eiricbPeso;
    }

    public String getEiricbSellos() {
        return eiricbSellos;
    }

    public void setEiricbSellos(String eiricbSellos) {
        this.eiricbSellos = eiricbSellos;
    }

    public String getEiricbCarga() {
        return eiricbCarga;
    }

    public void setEiricbCarga(String eiricbCarga) {
        this.eiricbCarga = eiricbCarga;
    }

    public String getEiricbTempera() {
        return eiricbTempera;
    }

    public void setEiricbTempera(String eiricbTempera) {
        this.eiricbTempera = eiricbTempera;
    }

    public String getEiricbVentilac() {
        return eiricbVentilac;
    }

    public void setEiricbVentilac(String eiricbVentilac) {
        this.eiricbVentilac = eiricbVentilac;
    }

    public String getEiricbTransporCode() {
        return eiricbTransporCode;
    }

    public void setEiricbTransporCode(String eiricbTransporCode) {
        this.eiricbTransporCode = eiricbTransporCode;
    }

    public String getEiricbTransporName() {
        return eiricbTransporName;
    }

    public void setEiricbTransporName(String eiricbTransporName) {
        this.eiricbTransporName = eiricbTransporName;
    }

    public String getEiricbExpimpRuc() {
        return eiricbExpimpRuc;
    }

    public void setEiricbExpimpRuc(String eiricbExpimpRuc) {
        this.eiricbExpimpRuc = eiricbExpimpRuc;
    }

    public String getEiricbExpimpName() {
        return eiricbExpimpName;
    }

    public void setEiricbExpimpName(String eiricbExpimpName) {
        this.eiricbExpimpName = eiricbExpimpName;
    }

    public String getEiricbNaviera() {
        return eiricbNaviera;
    }

    public void setEiricbNaviera(String eiricbNaviera) {
        this.eiricbNaviera = eiricbNaviera;
    }

    public String getEiricbMaxgross() {
        return eiricbMaxgross;
    }

    public void setEiricbMaxgross(String eiricbMaxgross) {
        this.eiricbMaxgross = eiricbMaxgross;
    }

    public String getEiricbTare() {
        return eiricbTare;
    }

    public void setEiricbTare(String eiricbTare) {
        this.eiricbTare = eiricbTare;
    }

    public String getEiricbIso() {
        return eiricbIso;
    }

    public void setEiricbIso(String eiricbIso) {
        this.eiricbIso = eiricbIso;
    }

    public String getEiricbManufactured() {
        return eiricbManufactured;
    }

    public void setEiricbManufactured(String eiricbManufactured) {
        this.eiricbManufactured = eiricbManufactured;
    }

    public String getEiricbValorHubo() {
        return eiricbValorHubo;
    }

    public void setEiricbValorHubo(String eiricbValorHubo) {
        this.eiricbValorHubo = eiricbValorHubo;
    }

    public String getEiricbValorGene() {
        return eiricbValorGene;
    }

    public void setEiricbValorGene(String eiricbValorGene) {
        this.eiricbValorGene = eiricbValorGene;
    }

    public String getEiricbValorComb() {
        return eiricbValorComb;
    }

    public void setEiricbValorComb(String eiricbValorComb) {
        this.eiricbValorComb = eiricbValorComb;
    }

    public String getEiricbObserva1() {
        return eiricbObserva1;
    }

    public void setEiricbObserva1(String eiricbObserva1) {
        this.eiricbObserva1 = eiricbObserva1;
    }

    public Boolean getEiricbLltSelloIef() {
        return eiricbLltSelloIef;
    }

    public void setEiricbLltSelloIef(Boolean eiricbLltSelloIef) {
        this.eiricbLltSelloIef = eiricbLltSelloIef;
    }

    public Boolean getEiricbLltSelloDef() {
        return eiricbLltSelloDef;
    }

    public void setEiricbLltSelloDef(Boolean eiricbLltSelloDef) {
        this.eiricbLltSelloDef = eiricbLltSelloDef;
    }

    public Boolean getEiricbLltSelloIea() {
        return eiricbLltSelloIea;
    }

    public void setEiricbLltSelloIea(Boolean eiricbLltSelloIea) {
        this.eiricbLltSelloIea = eiricbLltSelloIea;
    }

    public Boolean getEiricbLltSelloDea() {
        return eiricbLltSelloDea;
    }

    public void setEiricbLltSelloDea(Boolean eiricbLltSelloDea) {
        this.eiricbLltSelloDea = eiricbLltSelloDea;
    }

    public String getEiricbLltSelloTanque1() {
        return eiricbLltSelloTanque1;
    }

    public void setEiricbLltSelloTanque1(String eiricbLltSelloTanque1) {
        this.eiricbLltSelloTanque1 = eiricbLltSelloTanque1;
    }

    public String getEiricbLltSelloTanque2() {
        return eiricbLltSelloTanque2;
    }

    public void setEiricbLltSelloTanque2(String eiricbLltSelloTanque2) {
        this.eiricbLltSelloTanque2 = eiricbLltSelloTanque2;
    }

    public String getGenrciUuid3() {
        return genrciUuid3;
    }

    public void setGenrciUuid3(String genrciUuid3) {
        this.genrciUuid3 = genrciUuid3;
    }

    public String getEiricbObserva2() {
        return eiricbObserva2;
    }

    public void setEiricbObserva2(String eiricbObserva2) {
        this.eiricbObserva2 = eiricbObserva2;
    }

    public String getGenrciUuid1() {
        return genrciUuid1;
    }

    public void setGenrciUuid1(String genrciUuid1) {
        this.genrciUuid1 = genrciUuid1;
    }

    public String getEiricbUser() {
        return eiricbUser;
    }

    public void setEiricbUser(String eiricbUser) {
        this.eiricbUser = eiricbUser;
    }

    public String getEiricbPtc() {
        return eiricbPtc;
    }

    public void setEiricbPtc(String eiricbPtc) {
        this.eiricbPtc = eiricbPtc;
    }

    public String getEiricbUserpatio() {
        return eiricbUserpatio;
    }

    public void setEiricbUserpatio(String eiricbUserpatio) {
        this.eiricbUserpatio = eiricbUserpatio;
    }

    public String getEiricbPtcpatio() {
        return eiricbPtcpatio;
    }

    public void setEiricbPtcpatio(String eiricbPtcpatio) {
        this.eiricbPtcpatio = eiricbPtcpatio;
    }

    public String getEiricbObspatio() {
        return eiricbObspatio;
    }

    public void setEiricbObspatio(String eiricbObspatio) {
        this.eiricbObspatio = eiricbObspatio;
    }

    public String getEiricbFechapatio() {
        return eiricbFechapatio;
    }

    public void setEiricbFechapatio(String eiricbFechapatio) {
        this.eiricbFechapatio = eiricbFechapatio;
    }

    public String getEiricbUsertecnico() {
        return eiricbUsertecnico;
    }

    public void setEiricbUsertecnico(String eiricbUsertecnico) {
        this.eiricbUsertecnico = eiricbUsertecnico;
    }

    public String getEiricbPtctecnico() {
        return eiricbPtctecnico;
    }

    public void setEiricbPtctecnico(String eiricbPtctecnico) {
        this.eiricbPtctecnico = eiricbPtctecnico;
    }

    public String getEiricbObstecnico() {
        return eiricbObstecnico;
    }

    public void setEiricbObstecnico(String eiricbObstecnico) {
        this.eiricbObstecnico = eiricbObstecnico;
    }

    public String getEiricbFechatecnico() {
        return eiricbFechatecnico;
    }

    public void setEiricbFechatecnico(String eiricbFechatecnico) {
        this.eiricbFechatecnico = eiricbFechatecnico;
    }

    public String getEiricbUsertaller() {
        return eiricbUsertaller;
    }

    public void setEiricbUsertaller(String eiricbUsertaller) {
        this.eiricbUsertaller = eiricbUsertaller;
    }

    public String getEiricbPtctaller() {
        return eiricbPtctaller;
    }

    public void setEiricbPtctaller(String eiricbPtctaller) {
        this.eiricbPtctaller = eiricbPtctaller;
    }

    public String getEiricbObstaller() {
        return eiricbObstaller;
    }

    public void setEiricbObstaller(String eiricbObstaller) {
        this.eiricbObstaller = eiricbObstaller;
    }

    public String getEiricbFechataller() {
        return eiricbFechataller;
    }

    public void setEiricbFechataller(String eiricbFechataller) {
        this.eiricbFechataller = eiricbFechataller;
    }

    public String getEiricbSync() {
        return eiricbSync;
    }

    public void setEiricbSync(String eiricbSync) {
        this.eiricbSync = eiricbSync;
    }

    public String getEiricbCreatedAt() {
        return eiricbCreatedAt;
    }

    public void setEiricbCreatedAt(String eiricbCreatedAt) {
        this.eiricbCreatedAt = eiricbCreatedAt;
    }

    public List<ContenedorInspeccion> getContenedor() {
        return contenedor;
    }

    public void setContenedor(List<ContenedorInspeccion> contenedor) {
        this.contenedor = contenedor;
    }

    public List<ChassisInspeccion> getChasis() {
        return chasis;
    }

    public void setChasis(List<ChassisInspeccion> chasis) {
        this.chasis = chasis;
    }

    public List<LlantaInspeccion> getLlanta() {
        return llanta;
    }

    public void setLlanta(List<LlantaInspeccion> llanta) {
        this.llanta = llanta;
    }

    public List<QrInspeccion> getQr() {
        return qr;
    }

    public void setQr(List<QrInspeccion> qr) {
        this.qr = qr;
    }

    public List<ImagenInspeccion> getImagen() {
        return imagen;
    }

    public void setImagen(List<ImagenInspeccion> imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eiricbUuid);
        dest.writeString(this.eiricbCodigo);
        dest.writeString(this.eiricbChasisproced);
        dest.writeValue(this.eiricbContlleno);
        dest.writeString(this.eiricbContproced);
        dest.writeString(this.eiricbTipoproc);
        dest.writeString(this.eiricbIdexterno);
        dest.writeString(this.eiricbNrotransaccion);
        dest.writeString(this.eiricbNroentrada);
        dest.writeString(this.eiricbPlaca);
        dest.writeString(this.eiricbBlbooking);
        dest.writeString(this.eiricbBuque);
        dest.writeString(this.eiricbViaje);
        dest.writeString(this.eiricbPuerto);
        dest.writeString(this.eiricbChasis);
        dest.writeString(this.eiricbGenset);
        dest.writeString(this.eiricbChoferCedu);
        dest.writeString(this.eiricbChoferName);
        dest.writeString(this.eiricbContenedor);
        dest.writeString(this.genrciUuid2);
        dest.writeString(this.eiricbPeso);
        dest.writeString(this.eiricbSellos);
        dest.writeString(this.eiricbCarga);
        dest.writeString(this.eiricbTempera);
        dest.writeString(this.eiricbVentilac);
        dest.writeString(this.eiricbTransporCode);
        dest.writeString(this.eiricbTransporName);
        dest.writeString(this.eiricbExpimpRuc);
        dest.writeString(this.eiricbExpimpName);
        dest.writeString(this.eiricbNaviera);
        dest.writeString(this.eiricbMaxgross);
        dest.writeString(this.eiricbTare);
        dest.writeString(this.eiricbIso);
        dest.writeString(this.eiricbManufactured);
        dest.writeString(this.eiricbValorHubo);
        dest.writeString(this.eiricbValorGene);
        dest.writeString(this.eiricbValorComb);
        dest.writeString(this.eiricbObserva1);
        dest.writeValue(this.eiricbLltSelloIef);
        dest.writeValue(this.eiricbLltSelloDef);
        dest.writeValue(this.eiricbLltSelloIea);
        dest.writeValue(this.eiricbLltSelloDea);
        dest.writeString(this.eiricbLltSelloTanque1);
        dest.writeString(this.eiricbLltSelloTanque2);
        dest.writeString(this.genrciUuid3);
        dest.writeString(this.eiricbObserva2);
        dest.writeString(this.genrciUuid1);
        dest.writeString(this.eiricbUser);
        dest.writeString(this.eiricbPtc);
        dest.writeString(this.eiricbUserpatio);
        dest.writeString(this.eiricbPtcpatio);
        dest.writeString(this.eiricbObspatio);
        dest.writeString(this.eiricbFechapatio);
        dest.writeString(this.eiricbUsertecnico);
        dest.writeString(this.eiricbPtctecnico);
        dest.writeString(this.eiricbObstecnico);
        dest.writeString(this.eiricbFechatecnico);
        dest.writeString(this.eiricbUsertaller);
        dest.writeString(this.eiricbPtctaller);
        dest.writeString(this.eiricbObstaller);
        dest.writeString(this.eiricbFechataller);
        dest.writeString(this.eiricbSync);
        dest.writeString(this.eiricbCreatedAt);
        dest.writeTypedList(this.contenedor);
        dest.writeTypedList(this.chasis);
        dest.writeTypedList(this.llanta);
        dest.writeList(this.qr);
        dest.writeList(this.imagen);
    }

    public InspeccionEntity() {
    }

    protected InspeccionEntity(Parcel in) {
        this.eiricbUuid = in.readString();
        this.eiricbCodigo = in.readString();
        this.eiricbChasisproced = in.readString();
        this.eiricbContlleno = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.eiricbContproced = in.readString();
        this.eiricbTipoproc = in.readString();
        this.eiricbIdexterno = in.readString();
        this.eiricbNrotransaccion = in.readString();
        this.eiricbNroentrada = in.readString();
        this.eiricbPlaca = in.readString();
        this.eiricbBlbooking = in.readString();
        this.eiricbBuque = in.readString();
        this.eiricbViaje = in.readString();
        this.eiricbPuerto = in.readString();
        this.eiricbChasis = in.readString();
        this.eiricbGenset = in.readString();
        this.eiricbChoferCedu = in.readString();
        this.eiricbChoferName = in.readString();
        this.eiricbContenedor = in.readString();
        this.genrciUuid2 = in.readString();
        this.eiricbPeso = in.readString();
        this.eiricbSellos = in.readString();
        this.eiricbCarga = in.readString();
        this.eiricbTempera = in.readString();
        this.eiricbVentilac = in.readString();
        this.eiricbTransporCode = in.readString();
        this.eiricbTransporName = in.readString();
        this.eiricbExpimpRuc = in.readString();
        this.eiricbExpimpName = in.readString();
        this.eiricbNaviera = in.readString();
        this.eiricbMaxgross = in.readString();
        this.eiricbTare = in.readString();
        this.eiricbIso = in.readString();
        this.eiricbManufactured = in.readString();
        this.eiricbValorHubo = in.readString();
        this.eiricbValorGene = in.readString();
        this.eiricbValorComb = in.readString();
        this.eiricbObserva1 = in.readString();
        this.eiricbLltSelloIef = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.eiricbLltSelloDef = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.eiricbLltSelloIea = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.eiricbLltSelloDea = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.eiricbLltSelloTanque1 = in.readString();
        this.eiricbLltSelloTanque2 = in.readString();
        this.genrciUuid3 = in.readString();
        this.eiricbObserva2 = in.readString();
        this.genrciUuid1 = in.readString();
        this.eiricbUser = in.readString();
        this.eiricbPtc = in.readString();
        this.eiricbUserpatio = in.readString();
        this.eiricbPtcpatio = in.readString();
        this.eiricbObspatio = in.readString();
        this.eiricbFechapatio = in.readString();
        this.eiricbUsertecnico = in.readString();
        this.eiricbPtctecnico = in.readString();
        this.eiricbObstecnico = in.readString();
        this.eiricbFechatecnico = in.readString();
        this.eiricbUsertaller = in.readString();
        this.eiricbPtctaller = in.readString();
        this.eiricbObstaller = in.readString();
        this.eiricbFechataller = in.readString();
        this.eiricbSync = in.readString();
        this.eiricbCreatedAt = in.readString();
        this.contenedor = in.createTypedArrayList(ContenedorInspeccion.CREATOR);
        this.chasis = in.createTypedArrayList(ChassisInspeccion.CREATOR);
        this.llanta = in.createTypedArrayList(LlantaInspeccion.CREATOR);
        this.qr = new ArrayList<QrInspeccion>();
        in.readList(this.qr, QrInspeccion.class.getClassLoader());
        this.imagen = new ArrayList<ImagenInspeccion>();
        in.readList(this.imagen, ImagenInspeccion.class.getClassLoader());
    }

    public static final Creator<InspeccionEntity> CREATOR = new Creator<InspeccionEntity>() {
        @Override
        public InspeccionEntity createFromParcel(Parcel source) {
            return new InspeccionEntity(source);
        }

        @Override
        public InspeccionEntity[] newArray(int size) {
            return new InspeccionEntity[size];
        }
    };
}

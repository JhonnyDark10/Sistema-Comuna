package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sis_factura database table.
 * 
 */
@Entity
@Table(name="sis_factura")
@NamedQueries({
@NamedQuery(name="SisFactura.findAll", query="SELECT s FROM SisFactura s"),
@NamedQuery(name="SisFacturaBusca.findAll", query="SELECT s FROM SisFactura s where s.estadoFactura = 'C' and s.id_Factura = :patron"),
@NamedQuery(name="SisFacturaBuscaActivas.findAll", query="SELECT s FROM SisFactura s where s.estadoFactura = 'A' and s.id_Factura = :patron"),
})
public class SisFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_Factura;

	private String descripcion;

	private String estadoFactura;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Time hora;

	@Column(name="subtotal_factura")
	private float subtotalFactura;

	@Column(name="total_factura")
	private float totalFactura;

	//bi-directional many-to-one association to SisControlpago
	@OneToMany(mappedBy="sisFactura")
	private List<SisControlpago> sisControlpagos;

	//bi-directional many-to-one association to SisDetallefacturaservicio
	@OneToMany(mappedBy="sisFactura")
	private List<SisDetallefacturaservicio> sisDetallefacturaservicios;

	//bi-directional many-to-one association to SisFormapago
	@ManyToOne
	@JoinColumn(name="fk_formaPago")
	private SisFormapago sisFormapago;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="fk_usuario")
	private SegUsuario segUsuario;

	public SisFactura() {
	}

	public int getId_Factura() {
		return this.id_Factura;
	}

	public void setId_Factura(int id_Factura) {
		this.id_Factura = id_Factura;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoFactura() {
		return this.estadoFactura;
	}

	public void setEstadoFactura(String estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public float getSubtotalFactura() {
		return this.subtotalFactura;
	}

	public void setSubtotalFactura(float subtotalFactura) {
		this.subtotalFactura = subtotalFactura;
	}

	public float getTotalFactura() {
		return this.totalFactura;
	}

	public void setTotalFactura(float totalFactura) {
		this.totalFactura = totalFactura;
	}

	public List<SisControlpago> getSisControlpagos() {
		return this.sisControlpagos;
	}

	public void setSisControlpagos(List<SisControlpago> sisControlpagos) {
		this.sisControlpagos = sisControlpagos;
	}

	public SisControlpago addSisControlpago(SisControlpago sisControlpago) {
		getSisControlpagos().add(sisControlpago);
		sisControlpago.setSisFactura(this);

		return sisControlpago;
	}

	public SisControlpago removeSisControlpago(SisControlpago sisControlpago) {
		getSisControlpagos().remove(sisControlpago);
		sisControlpago.setSisFactura(null);

		return sisControlpago;
	}

	public List<SisDetallefacturaservicio> getSisDetallefacturaservicios() {
		return this.sisDetallefacturaservicios;
	}

	public void setSisDetallefacturaservicios(List<SisDetallefacturaservicio> sisDetallefacturaservicios) {
		this.sisDetallefacturaservicios = sisDetallefacturaservicios;
	}

	public SisDetallefacturaservicio addSisDetallefacturaservicio(SisDetallefacturaservicio sisDetallefacturaservicio) {
		getSisDetallefacturaservicios().add(sisDetallefacturaservicio);
		sisDetallefacturaservicio.setSisFactura(this);

		return sisDetallefacturaservicio;
	}

	public SisDetallefacturaservicio removeSisDetallefacturaservicio(SisDetallefacturaservicio sisDetallefacturaservicio) {
		getSisDetallefacturaservicios().remove(sisDetallefacturaservicio);
		sisDetallefacturaservicio.setSisFactura(null);

		return sisDetallefacturaservicio;
	}

	public SisFormapago getSisFormapago() {
		return this.sisFormapago;
	}

	public void setSisFormapago(SisFormapago sisFormapago) {
		this.sisFormapago = sisFormapago;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}
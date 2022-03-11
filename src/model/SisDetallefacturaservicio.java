package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_detallefacturaservicios database table.
 * 
 */
@Entity
@Table(name="sis_detallefacturaservicios")
@NamedQueries({
@NamedQuery(name="SisDetallefacturaservicio.findAll", query="SELECT s FROM SisDetallefacturaservicio s"),
@NamedQuery(name="SisDetallePorId.findAll", query="SELECT s FROM SisDetallefacturaservicio s where s.sisFactura.id_Factura = :patron and s.estadoServ = 'A'")
})

public class SisDetallefacturaservicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_DetalleServicios;

	private int cantidad;

	private String estadoServ;

	//bi-directional many-to-one association to SisServicio
	@ManyToOne
	@JoinColumn(name="fk_Servicios")
	private SisServicio sisServicio;

	//bi-directional many-to-one association to SisFactura
	@ManyToOne
	@JoinColumn(name="fk_Factura")
	private SisFactura sisFactura;

	public SisDetallefacturaservicio() {
	}

	public int getId_DetalleServicios() {
		return this.id_DetalleServicios;
	}

	public void setId_DetalleServicios(int id_DetalleServicios) {
		this.id_DetalleServicios = id_DetalleServicios;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstadoServ() {
		return this.estadoServ;
	}

	public void setEstadoServ(String estadoServ) {
		this.estadoServ = estadoServ;
	}

	public SisServicio getSisServicio() {
		return this.sisServicio;
	}

	public void setSisServicio(SisServicio sisServicio) {
		this.sisServicio = sisServicio;
	}

	public SisFactura getSisFactura() {
		return this.sisFactura;
	}

	public void setSisFactura(SisFactura sisFactura) {
		this.sisFactura = sisFactura;
	}

}
package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the vistalistadopagoscomuneros database table.
 * 
 */
@Entity
@Table(name="vistalistadopagoscomuneros")
@NamedQuery(name="Vistalistadopagoscomunero.findAll", query="SELECT v FROM Vistalistadopagoscomunero v where LOWER(v.cedula) LIKE :patron")
public class Vistalistadopagoscomunero implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categoria;

	private String cedula;

	private String estadoFactura;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Id
	private int id_Factura;

	private String personaC;

	private float total;

	public Vistalistadopagoscomunero() {
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public int getId_Factura() {
		return this.id_Factura;
	}

	public void setId_Factura(int id_Factura) {
		this.id_Factura = id_Factura;
	}

	public String getPersonaC() {
		return this.personaC;
	}

	public void setPersonaC(String personaC) {
		this.personaC = personaC;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}
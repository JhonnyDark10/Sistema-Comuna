package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_categoriapago database table.
 * 
 */
@Entity
@Table(name="sis_categoriapago")
@NamedQuery(name="SisCategoriapago.findAll", query="SELECT s FROM SisCategoriapago s where s.estadoPago = 'A' and LOWER(s.descripcionPago) LIKE :patron")
public class SisCategoriapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_CategoriaPago;

	private String descripcionPago;

	private String estadoPago;

	//bi-directional many-to-one association to SisControlpago
	@OneToMany(mappedBy="sisCategoriapago")
	private List<SisControlpago> sisControlpagos;

	public SisCategoriapago() {
	}

	public int getId_CategoriaPago() {
		return this.id_CategoriaPago;
	}

	public void setId_CategoriaPago(int id_CategoriaPago) {
		this.id_CategoriaPago = id_CategoriaPago;
	}

	public String getDescripcionPago() {
		return this.descripcionPago;
	}

	public void setDescripcionPago(String descripcionPago) {
		this.descripcionPago = descripcionPago;
	}

	public String getEstadoPago() {
		return this.estadoPago;
	}

	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}

	public List<SisControlpago> getSisControlpagos() {
		return this.sisControlpagos;
	}

	public void setSisControlpagos(List<SisControlpago> sisControlpagos) {
		this.sisControlpagos = sisControlpagos;
	}

	public SisControlpago addSisControlpago(SisControlpago sisControlpago) {
		getSisControlpagos().add(sisControlpago);
		sisControlpago.setSisCategoriapago(this);

		return sisControlpago;
	}

	public SisControlpago removeSisControlpago(SisControlpago sisControlpago) {
		getSisControlpagos().remove(sisControlpago);
		sisControlpago.setSisCategoriapago(null);

		return sisControlpago;
	}

}
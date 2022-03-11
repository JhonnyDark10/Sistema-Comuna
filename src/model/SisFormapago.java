package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_formapago database table.
 * 
 */
@Entity
@Table(name="sis_formapago")
@NamedQuery(name="SisFormapago.findAll", query="SELECT s FROM SisFormapago s where s.estadoForma = 'A' and LOWER(s.descripcionForma) LIKE :patron")
public class SisFormapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_FormaPago;

	private String descripcionForma;

	private String estadoForma;

	//bi-directional many-to-one association to SisFactura
	@OneToMany(mappedBy="sisFormapago")
	private List<SisFactura> sisFacturas;

	public SisFormapago() {
	}

	public int getId_FormaPago() {
		return this.id_FormaPago;
	}

	public void setId_FormaPago(int id_FormaPago) {
		this.id_FormaPago = id_FormaPago;
	}

	public String getDescripcionForma() {
		return this.descripcionForma;
	}

	public void setDescripcionForma(String descripcionForma) {
		this.descripcionForma = descripcionForma;
	}

	public String getEstadoForma() {
		return this.estadoForma;
	}

	public void setEstadoForma(String estadoForma) {
		this.estadoForma = estadoForma;
	}

	public List<SisFactura> getSisFacturas() {
		return this.sisFacturas;
	}

	public void setSisFacturas(List<SisFactura> sisFacturas) {
		this.sisFacturas = sisFacturas;
	}

	public SisFactura addSisFactura(SisFactura sisFactura) {
		getSisFacturas().add(sisFactura);
		sisFactura.setSisFormapago(this);

		return sisFactura;
	}

	public SisFactura removeSisFactura(SisFactura sisFactura) {
		getSisFacturas().remove(sisFactura);
		sisFactura.setSisFormapago(null);

		return sisFactura;
	}

}
package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_controlpagos database table.
 * 
 */
@Entity
@Table(name="sis_controlpagos")
@NamedQuery(name="SisControlpago.findAll", query="SELECT s FROM SisControlpago s ")
public class SisControlpago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_ControlPagos;

	private String estadoServ;

	private float valorTotal;

	//bi-directional many-to-one association to SisFactura
	@ManyToOne
	@JoinColumn(name="fk_factura")
	private SisFactura sisFactura;

	//bi-directional many-to-one association to SisCategoriapago
	@ManyToOne
	@JoinColumn(name="fk_CategoriaPagos")
	private SisCategoriapago sisCategoriapago;

	public SisControlpago() {
	}

	public int getId_ControlPagos() {
		return this.id_ControlPagos;
	}

	public void setId_ControlPagos(int id_ControlPagos) {
		this.id_ControlPagos = id_ControlPagos;
	}

	public String getEstadoServ() {
		return this.estadoServ;
	}

	public void setEstadoServ(String estadoServ) {
		this.estadoServ = estadoServ;
	}

	public float getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public SisFactura getSisFactura() {
		return this.sisFactura;
	}

	public void setSisFactura(SisFactura sisFactura) {
		this.sisFactura = sisFactura;
	}

	public SisCategoriapago getSisCategoriapago() {
		return this.sisCategoriapago;
	}

	public void setSisCategoriapago(SisCategoriapago sisCategoriapago) {
		this.sisCategoriapago = sisCategoriapago;
	}

}
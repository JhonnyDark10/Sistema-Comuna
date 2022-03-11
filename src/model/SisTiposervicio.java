package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_tiposervicios database table.
 * 
 */
@Entity
@Table(name="sis_tiposervicios")
@NamedQuery(name="SisTiposervicio.findAll", query="SELECT s FROM SisTiposervicio s where s.estadoServicios = 'A' and LOWER(s.descripcionServicios) LIKE :patron")
public class SisTiposervicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_tipoServicios;

	private String descripcionServicios;

	private String estadoServicios;

	//bi-directional many-to-one association to SisServicio
	@OneToMany(mappedBy="sisTiposervicio")
	private List<SisServicio> sisServicios;

	public SisTiposervicio() {
	}

	public int getId_tipoServicios() {
		return this.id_tipoServicios;
	}

	public void setId_tipoServicios(int id_tipoServicios) {
		this.id_tipoServicios = id_tipoServicios;
	}

	public String getDescripcionServicios() {
		return this.descripcionServicios;
	}

	public void setDescripcionServicios(String descripcionServicios) {
		this.descripcionServicios = descripcionServicios;
	}

	public String getEstadoServicios() {
		return this.estadoServicios;
	}

	public void setEstadoServicios(String estadoServicios) {
		this.estadoServicios = estadoServicios;
	}

	public List<SisServicio> getSisServicios() {
		return this.sisServicios;
	}

	public void setSisServicios(List<SisServicio> sisServicios) {
		this.sisServicios = sisServicios;
	}

	public SisServicio addSisServicio(SisServicio sisServicio) {
		getSisServicios().add(sisServicio);
		sisServicio.setSisTiposervicio(this);

		return sisServicio;
	}

	public SisServicio removeSisServicio(SisServicio sisServicio) {
		getSisServicios().remove(sisServicio);
		sisServicio.setSisTiposervicio(null);

		return sisServicio;
	}

}
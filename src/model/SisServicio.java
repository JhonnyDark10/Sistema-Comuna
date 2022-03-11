package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_servicios database table.
 * 
 */
@Entity
@Table(name="sis_servicios")
@NamedQuery(name="SisServicio.findAll", query="SELECT s FROM SisServicio s where s.estadoServ ='A' and LOWER(s.desServicios) LIKE :patron")
public class SisServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_Servicios;

	private String desServicios;

	private String estadoServ;

	private float valorServicios;

	//bi-directional many-to-one association to SisDetallefacturaservicio
	@OneToMany(mappedBy="sisServicio")
	private List<SisDetallefacturaservicio> sisDetallefacturaservicios;

	//bi-directional many-to-one association to SisTiposervicio
	@ManyToOne
	@JoinColumn(name="fk_tipoServicios")
	private SisTiposervicio sisTiposervicio;

	public SisServicio() {
	}

	public int getId_Servicios() {
		return this.id_Servicios;
	}

	public void setId_Servicios(int id_Servicios) {
		this.id_Servicios = id_Servicios;
	}

	public String getDesServicios() {
		return this.desServicios;
	}

	public void setDesServicios(String desServicios) {
		this.desServicios = desServicios;
	}

	public String getEstadoServ() {
		return this.estadoServ;
	}

	public void setEstadoServ(String estadoServ) {
		this.estadoServ = estadoServ;
	}

	public float getValorServicios() {
		return this.valorServicios;
	}

	public void setValorServicios(float valorServicios) {
		this.valorServicios = valorServicios;
	}

	public List<SisDetallefacturaservicio> getSisDetallefacturaservicios() {
		return this.sisDetallefacturaservicios;
	}

	public void setSisDetallefacturaservicios(List<SisDetallefacturaservicio> sisDetallefacturaservicios) {
		this.sisDetallefacturaservicios = sisDetallefacturaservicios;
	}

	public SisDetallefacturaservicio addSisDetallefacturaservicio(SisDetallefacturaservicio sisDetallefacturaservicio) {
		getSisDetallefacturaservicios().add(sisDetallefacturaservicio);
		sisDetallefacturaservicio.setSisServicio(this);

		return sisDetallefacturaservicio;
	}

	public SisDetallefacturaservicio removeSisDetallefacturaservicio(SisDetallefacturaservicio sisDetallefacturaservicio) {
		getSisDetallefacturaservicios().remove(sisDetallefacturaservicio);
		sisDetallefacturaservicio.setSisServicio(null);

		return sisDetallefacturaservicio;
	}

	public SisTiposervicio getSisTiposervicio() {
		return this.sisTiposervicio;
	}

	public void setSisTiposervicio(SisTiposervicio sisTiposervicio) {
		this.sisTiposervicio = sisTiposervicio;
	}

}
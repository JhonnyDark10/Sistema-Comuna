package model;

import java.io.Serializable;
import javax.persistence.*;


import java.util.Date;


/**
 * The persistent class for the sis_planificacion database table.
 * 
 */
@Entity
@Table(name="sis_planificacion")
@NamedQuery(name="SisPlanificacion.findAll", query="SELECT s FROM SisPlanificacion s where s.estadoPlan = 'A' and (s.fecha >= :patron1  and s.fecha <= :patron2)")
public class SisPlanificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_Planificacion;

	private String celular;

	private String contacto;

	private String descripcion;

	private String direccion;

	private String estadoPlan;

	@Temporal(TemporalType.DATE)
	private Date fecha;


	private String hora;

	private String lugar;

	//bi-directional many-to-one association to SisPlanTiptarea
	@ManyToOne
	@JoinColumn(name="fk_tipotarea")
	private SisPlanTiptarea sisPlanTiptarea;

	//bi-directional many-to-one association to SisPlanDia
	@ManyToOne
	@JoinColumn(name="fk_dia")
	private SisPlanDia sisPlanDia;

	
	//bi-directional many-to-one association to SegPeriodoEncargo
		@ManyToOne
		@JoinColumn(name="fk_usuario")
		private SegPeriodoEncargo segPeriodoEncargo;
	
	public SisPlanificacion() {
	}

	public int getId_Planificacion() {
		return this.id_Planificacion;
	}

	public void setId_Planificacion(int id_Planificacion) {
		this.id_Planificacion = id_Planificacion;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstadoPlan() {
		return this.estadoPlan;
	}

	public void setEstadoPlan(String estadoPlan) {
		this.estadoPlan = estadoPlan;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public SisPlanTiptarea getSisPlanTiptarea() {
		return this.sisPlanTiptarea;
	}

	public void setSisPlanTiptarea(SisPlanTiptarea sisPlanTiptarea) {
		this.sisPlanTiptarea = sisPlanTiptarea;
	}

	public SisPlanDia getSisPlanDia() {
		return this.sisPlanDia;
	}

	public void setSisPlanDia(SisPlanDia sisPlanDia) {
		this.sisPlanDia = sisPlanDia;
	}

	public SegPeriodoEncargo getSegPeriodoEncargo() {
		return segPeriodoEncargo;
	}

	public void setSegPeriodoEncargo(SegPeriodoEncargo segPeriodoEncargo) {
		this.segPeriodoEncargo = segPeriodoEncargo;
	}

	
}
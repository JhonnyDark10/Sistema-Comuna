package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the seg_usuario database table.
 * 
 */
@Entity
@Table(name="seg_usuario")
@NamedQueries({
	@NamedQuery(name="SegUsuarioTodo.findAll", query="SELECT s FROM SegUsuario s where s.estado = 'A' and LOWER(s.cedula) LIKE :patron"),
	@NamedQuery(name="SegUsuario.findAll", query="SELECT s FROM SegUsuario s"),
	@NamedQuery(name="SegUsuario.buscaUsuario", 
	query="SELECT s FROM SegUsuario s WHERE s.usuario = :nombreUsuario and s.estado = 'A'"),
	@NamedQuery(name="SegUsuario.buscarPorUsuario", 
	query="SELECT s FROM SegUsuario s where s.usuario = :patron and s.idUsuario <> :idUsuario and s.estado = 'A'"),
	@NamedQuery(name="SegUsuario.buscarCambioClave", 
	query="SELECT s FROM SegUsuario s where s.idUsuario = :patron and s.verifica = 1 and s.estado='A'"),
	
	@NamedQuery(name="SegUsuario.buscarSoloComuneros", 
	query="SELECT s FROM SegUsuario s where s.cedula = :patron and s.estado='A' and s.segPerfil.id_perfil = 2"),
	
	@NamedQuery(name="SegUsuario.buscarDireccionesMapa", 
	query="SELECT s FROM SegUsuario s where s.estado='A' and s.segPerfil.id_perfil = 2"),
	
	
	@NamedQuery(name="SegUsuario.buscarSoloClientes", 
	query="SELECT s FROM SegUsuario s where s.cedula = :patron and s.estado='A' and s.segPerfil.id_perfil = 3"),
	
	@NamedQuery(name="SegUsuario.buscarSoloComunerosCedula", 
	query="SELECT s FROM SegUsuario s where LOWER(s.cedula) LIKE :patron and s.estado='A' and s.segPerfil.id_perfil = 2"),
	
	
})
public class SegUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int idUsuario;

	@Column(name="APELLIDO_MATERNO")
	private String apellidoMaterno;

	@Column(name="APELLIDO_PATERNO")
	private String apellidoPaterno;

	private String cedula;


	
	private String celular;

	private Double latitud;
	
	private Double longitud;
	
	private String clave;

	private int controlador;

	private String email;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	private Time hora;

	private String nacionalidad;

	private Date createdAtDate;
	
	private Time createdAtTime;
	
	private String nombre;

	private String telefono;

	private String usuario;

	private int verifica;

	//bi-directional many-to-one association to SegPeriodoEncargo
	@OneToMany(mappedBy="segUsuario")
	private List<SegPeriodoEncargo> segPeriodoEncargos;

	//bi-directional many-to-one association to SegUsuariogenero
	@ManyToOne
	@JoinColumn(name="id_fk_genero")
	private SegUsuariogenero segUsuariogenero;

	//bi-directional many-to-one association to SegPerfil
	@ManyToOne
	@JoinColumn(name="id_fk_perfil")
	private SegPerfil segPerfil;

	//bi-directional many-to-one association to SisFactura
	@OneToMany(mappedBy="segUsuario")
	private List<SisFactura> sisFacturas;

	public SegUsuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getControlador() {
		return this.controlador;
	}

	public void setControlador(int controlador) {
		this.controlador = controlador;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getVerifica() {
		return this.verifica;
	}

	public void setVerifica(int verifica) {
		this.verifica = verifica;
	}

	public List<SegPeriodoEncargo> getSegPeriodoEncargos() {
		return this.segPeriodoEncargos;
	}

	public void setSegPeriodoEncargos(List<SegPeriodoEncargo> segPeriodoEncargos) {
		this.segPeriodoEncargos = segPeriodoEncargos;
	}

	public SegPeriodoEncargo addSegPeriodoEncargo(SegPeriodoEncargo segPeriodoEncargo) {
		getSegPeriodoEncargos().add(segPeriodoEncargo);
		segPeriodoEncargo.setSegUsuario(this);

		return segPeriodoEncargo;
	}

	public SegPeriodoEncargo removeSegPeriodoEncargo(SegPeriodoEncargo segPeriodoEncargo) {
		getSegPeriodoEncargos().remove(segPeriodoEncargo);
		segPeriodoEncargo.setSegUsuario(null);

		return segPeriodoEncargo;
	}

	public SegUsuariogenero getSegUsuariogenero() {
		return this.segUsuariogenero;
	}

	public void setSegUsuariogenero(SegUsuariogenero segUsuariogenero) {
		this.segUsuariogenero = segUsuariogenero;
	}

	public SegPerfil getSegPerfil() {
		return this.segPerfil;
	}

	public void setSegPerfil(SegPerfil segPerfil) {
		this.segPerfil = segPerfil;
	}

	public List<SisFactura> getSisFacturas() {
		return this.sisFacturas;
	}

	public void setSisFacturas(List<SisFactura> sisFacturas) {
		this.sisFacturas = sisFacturas;
	}

	public SisFactura addSisFactura(SisFactura sisFactura) {
		getSisFacturas().add(sisFactura);
		sisFactura.setSegUsuario(this);

		return sisFactura;
	}

	public SisFactura removeSisFactura(SisFactura sisFactura) {
		getSisFacturas().remove(sisFactura);
		sisFactura.setSegUsuario(null);

		return sisFactura;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Date getCreatedAtDate() {
		return createdAtDate;
	}

	public void setCreatedAtDate(Date createdAtDate) {
		this.createdAtDate = createdAtDate;
	}

	public Time getCreatedAtTime() {
		return createdAtTime;
	}

	public void setCreatedAtTime(Time createdAtTime) {
		this.createdAtTime = createdAtTime;
	}

	

	

}
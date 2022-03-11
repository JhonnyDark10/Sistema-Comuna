package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the seg_periodo_encargo database table.
 * 
 */
@Entity
@Table(name="seg_periodo_encargo")
@NamedQueries({
@NamedQuery(name="SegPeriodoEncargo.findAll", query="SELECT s FROM SegPeriodoEncargo s where s.estado = 'A' and LOWER(s.segUsuario.cedula) LIKE :patron"),
@NamedQuery(name="SegPeriodoTodos.findAll", query="SELECT s FROM SegPeriodoEncargo s" ),
@NamedQuery(name="SegPeriodoRecupera.findAll", query="SELECT s FROM SegPeriodoEncargo s where s.idPeriodo = :patron" )
})

public class SegPeriodoEncargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_periodo")
	private int idPeriodo;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name="periodo_desde")
	private Date periodoDesde;

	@Temporal(TemporalType.DATE)
	@Column(name="periodo_hasta")
	private Date periodoHasta;

	@Column(name="valor_ingreso")
	private float valorIngreso;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="fk_seg_usuario")
	private SegUsuario segUsuario;
	
	//este es.....el aumentp
	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="segPeriodoEncargo")
	private List<SisPlanificacion> sisPlanificacion;
	

	public SegPeriodoEncargo() {
	}

	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
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

	public Date getPeriodoDesde() {
		return this.periodoDesde;
	}

	public void setPeriodoDesde(Date periodoDesde) {
		this.periodoDesde = periodoDesde;
	}

	public Date getPeriodoHasta() {
		return this.periodoHasta;
	}

	public void setPeriodoHasta(Date periodoHasta) {
		this.periodoHasta = periodoHasta;
	}

	public float getValorIngreso() {
		return this.valorIngreso;
	}

	public void setValorIngreso(float valorIngreso) {
		this.valorIngreso = valorIngreso;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

	
	
	
	
	
	
	public List<SisPlanificacion> getSisPlanificacion() {
		return sisPlanificacion;
	}

	public void setSisPlanificacion(List<SisPlanificacion> sisPlanificacion) {
		this.sisPlanificacion = sisPlanificacion;
	}

	public SisPlanificacion addSisPlanificacion(SisPlanificacion sisPlanificacion) {
		getSisPlanificacion().add(sisPlanificacion);
		sisPlanificacion.setSegPeriodoEncargo(this);

		return sisPlanificacion;
	}

	public SisPlanificacion removeSegPeriodoEncargo(SisPlanificacion sisPlanificacion) {
		getSisPlanificacion().remove(sisPlanificacion);
		sisPlanificacion.setSegPeriodoEncargo(null);

		return sisPlanificacion;
	}
}
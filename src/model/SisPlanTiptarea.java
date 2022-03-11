package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_plan_tiptareas database table.
 * 
 */
@Entity
@Table(name="sis_plan_tiptareas")
@NamedQuery(name="SisPlanTiptarea.findAll", query="SELECT s FROM SisPlanTiptarea s  where s.estadoTTarea = 'A' and LOWER(s.descripcion) LIKE :patron")
public class SisPlanTiptarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_TipoTarea;

	private String descripcion;

	private String estadoTTarea;

	//bi-directional many-to-one association to SisPlanificacion
	@OneToMany(mappedBy="sisPlanTiptarea")
	private List<SisPlanificacion> sisPlanificacions;

	public SisPlanTiptarea() {
	}

	public int getId_TipoTarea() {
		return this.id_TipoTarea;
	}

	public void setId_TipoTarea(int id_TipoTarea) {
		this.id_TipoTarea = id_TipoTarea;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoTTarea() {
		return this.estadoTTarea;
	}

	public void setEstadoTTarea(String estadoTTarea) {
		this.estadoTTarea = estadoTTarea;
	}

	public List<SisPlanificacion> getSisPlanificacions() {
		return this.sisPlanificacions;
	}

	public void setSisPlanificacions(List<SisPlanificacion> sisPlanificacions) {
		this.sisPlanificacions = sisPlanificacions;
	}

	public SisPlanificacion addSisPlanificacion(SisPlanificacion sisPlanificacion) {
		getSisPlanificacions().add(sisPlanificacion);
		sisPlanificacion.setSisPlanTiptarea(this);

		return sisPlanificacion;
	}

	public SisPlanificacion removeSisPlanificacion(SisPlanificacion sisPlanificacion) {
		getSisPlanificacions().remove(sisPlanificacion);
		sisPlanificacion.setSisPlanTiptarea(null);

		return sisPlanificacion;
	}

}
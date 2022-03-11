package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_plan_dias database table.
 * 
 */
@Entity
@Table(name="sis_plan_dias")
@NamedQuery(name="SisPlanDia.findAll", query="SELECT s FROM SisPlanDia s  where s.estadoDia = 'A' and LOWER(s.descripcion) LIKE :patron")
public class SisPlanDia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_Dias;

	private String descripcion;

	private String estadoDia;

	//bi-directional many-to-one association to SisPlanificacion
	@OneToMany(mappedBy="sisPlanDia")
	private List<SisPlanificacion> sisPlanificacions;

	public SisPlanDia() {
	}

	public int getId_Dias() {
		return this.id_Dias;
	}

	public void setId_Dias(int id_Dias) {
		this.id_Dias = id_Dias;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoDia() {
		return this.estadoDia;
	}

	public void setEstadoDia(String estadoDia) {
		this.estadoDia = estadoDia;
	}

	public List<SisPlanificacion> getSisPlanificacions() {
		return this.sisPlanificacions;
	}

	public void setSisPlanificacions(List<SisPlanificacion> sisPlanificacions) {
		this.sisPlanificacions = sisPlanificacions;
	}

	public SisPlanificacion addSisPlanificacion(SisPlanificacion sisPlanificacion) {
		getSisPlanificacions().add(sisPlanificacion);
		sisPlanificacion.setSisPlanDia(this);

		return sisPlanificacion;
	}

	public SisPlanificacion removeSisPlanificacion(SisPlanificacion sisPlanificacion) {
		getSisPlanificacions().remove(sisPlanificacion);
		sisPlanificacion.setSisPlanDia(null);

		return sisPlanificacion;
	}

}
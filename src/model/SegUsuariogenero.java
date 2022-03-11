package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_usuariogenero database table.
 * 
 */
@Entity
@Table(name="seg_usuariogenero")
@NamedQuery(name="SegUsuariogenero.findAll", query="SELECT s FROM SegUsuariogenero s where s.estado = 'A' and LOWER(s.descripcionGenero) LIKE :patron")
public class SegUsuariogenero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_genero")
	private int idGenero;

	private String descripcionGenero;

	private String estado;

	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="segUsuariogenero")
	private List<SegUsuario> segUsuarios;

	public SegUsuariogenero() {
	}

	public int getIdGenero() {
		return this.idGenero;
	}

	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}

	public String getDescripcionGenero() {
		return this.descripcionGenero;
	}

	public void setDescripcionGenero(String descripcionGenero) {
		this.descripcionGenero = descripcionGenero;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<SegUsuario> getSegUsuarios() {
		return this.segUsuarios;
	}

	public void setSegUsuarios(List<SegUsuario> segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().add(segUsuario);
		segUsuario.setSegUsuariogenero(this);

		return segUsuario;
	}

	public SegUsuario removeSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().remove(segUsuario);
		segUsuario.setSegUsuariogenero(null);

		return segUsuario;
	}

}
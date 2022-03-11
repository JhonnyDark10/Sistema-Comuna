package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisTiposervicioDAO extends ClaseDAO {

	//Metodos para que retorne todo los tipos de clientes 
	public List<SisTiposervicio> getListatiposervicios(String value){
		List<SisTiposervicio> retorno = new ArrayList<SisTiposervicio>();
		

		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		
		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("SisTiposervicio.findAll").setParameter("patron", value);
		retorno = (List<SisTiposervicio>)query.getResultList();
		return retorno;
	}
	
}
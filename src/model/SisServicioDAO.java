package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisServicioDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<SisServicio> getListaServicios(String value) {
		
		List<SisServicio> retorno = new ArrayList<SisServicio>();
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		
		Query query = getEntityManager().createNamedQuery("SisServicio.findAll").setParameter("patron", value);
		retorno = (List<SisServicio>) query.getResultList();
		return retorno;
	}
}








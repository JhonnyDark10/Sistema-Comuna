package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisPlanTiptareaDAO extends ClaseDAO{
	
	
	@SuppressWarnings("unchecked")
	public List<SisPlanTiptarea> getTipoTareas(String value) {
		List<SisPlanTiptarea> retorno = new ArrayList<SisPlanTiptarea>();
		
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("SisPlanTiptarea.findAll").setParameter("patron", value);
		retorno = (List<SisPlanTiptarea>) query.getResultList();
		return retorno;
	}
}

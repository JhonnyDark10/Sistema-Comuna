package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisPlanDiaDAO extends ClaseDAO{
	
	
	@SuppressWarnings("unchecked")
	public List<SisPlanDia> getTipoTareas(String value) {
		List<SisPlanDia> retorno = new ArrayList<SisPlanDia>();
		
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("SisPlanDia.findAll").setParameter("patron", value);
		retorno = (List<SisPlanDia>) query.getResultList();
		return retorno;
	}
}

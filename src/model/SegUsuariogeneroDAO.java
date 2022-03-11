package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SegUsuariogeneroDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SegUsuariogenero> getListaGeneros(String value) {
		List<SegUsuariogenero> retorno = new ArrayList<SegUsuariogenero>();
		
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("SegUsuariogenero.findAll").setParameter("patron", value);
		retorno = (List<SegUsuariogenero>) query.getResultList();
		return retorno;
	}
}

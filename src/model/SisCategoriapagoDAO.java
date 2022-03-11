package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisCategoriapagoDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<SisCategoriapago> getListaCategorias(String value) {
		List<SisCategoriapago> resultado = new ArrayList<SisCategoriapago>(); 
		

		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		
		
		Query query = getEntityManager().createNamedQuery("SisCategoriapago.findAll").setParameter("patron", value);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<SisCategoriapago>) query.getResultList();
		return resultado;
	}
	
	
}

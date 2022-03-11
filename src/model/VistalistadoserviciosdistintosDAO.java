package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class VistalistadoserviciosdistintosDAO extends ClaseDAO{

	
	@SuppressWarnings("unchecked")
	public List<Vistalistadoserviciosdistinto> getlistaServiciosRegistrados(String value){
		List<Vistalistadoserviciosdistinto> retorno = new ArrayList<Vistalistadoserviciosdistinto>();
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}

		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("Vistalistadoserviciosdistinto.findAll").setParameter("patron", value);
		
		
		retorno = (List<Vistalistadoserviciosdistinto>)query.getResultList();
		
		return retorno;
	}
	
}

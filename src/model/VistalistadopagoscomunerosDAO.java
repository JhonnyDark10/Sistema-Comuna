package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class VistalistadopagoscomunerosDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<Vistalistadopagoscomunero> getListaPagosComuneros(String value) {
		
		List<Vistalistadopagoscomunero> retorno = new ArrayList<Vistalistadopagoscomunero>();
		
		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		
		Query query = getEntityManager().createNamedQuery("Vistalistadopagoscomunero.findAll").setParameter("patron", value);
		retorno = (List<Vistalistadopagoscomunero>) query.getResultList();
		return retorno;
	}
}

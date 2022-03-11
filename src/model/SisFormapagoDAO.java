package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisFormapagoDAO extends ClaseDAO {

	//Metodos para que retorne todo los tipos de clientes 
	public List<SisFormapago> getListaFormas(String value){
		List<SisFormapago> retorno = new ArrayList<SisFormapago>();
		

		if (value == null || value.length() == 0) {
			value = "%";
		}else{
			value = "%" + value.toLowerCase() + "%";
		}
		
		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("SisFormapago.findAll").setParameter("patron", value);
		retorno = (List<SisFormapago>)query.getResultList();
		return retorno;
	}
	
}
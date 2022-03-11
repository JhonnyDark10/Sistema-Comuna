package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisControlpagoDAO extends ClaseDAO {

	//Metodos para que retorne todo los tipos de clientes 
	public List<SisControlpago> getListaPagos(){
		List<SisControlpago> retorno = new ArrayList<SisControlpago>();
		
		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("SisControlpago.findAll");
		retorno = (List<SisControlpago>)query.getResultList();
		return retorno;
	}
	
}
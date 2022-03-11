package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

public class SisPlanificacionDAO extends ClaseDAO{
	
	
	@SuppressWarnings("unchecked")
	public List<SisPlanificacion> getListaPlanificacione(Date fechaini, Date fechafin) {
		
		List<SisPlanificacion> retorno = new ArrayList<SisPlanificacion>();	
	
		Query query = getEntityManager().createNamedQuery("SisPlanificacion.findAll").setParameter("patron1", fechaini);
		query.setParameter("patron2", fechafin);
		retorno = (List<SisPlanificacion>) query.getResultList();
		return retorno;
	}
}

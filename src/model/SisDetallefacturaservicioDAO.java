package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisDetallefacturaservicioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SisDetallefacturaservicio> getListaDetalles() {
		List<SisDetallefacturaservicio> retorno = new ArrayList<SisDetallefacturaservicio>();
		
		
		Query query = getEntityManager().createNamedQuery("SisDetallefacturaservicio.findAll");
		retorno = (List<SisDetallefacturaservicio>) query.getResultList();
		return retorno;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SisDetallefacturaservicio> getListaPorIdFactura(int value) {
		List<SisDetallefacturaservicio> retorno = new ArrayList<SisDetallefacturaservicio>();
		
		
		Query query = getEntityManager().createNamedQuery("SisDetallePorId.findAll").setParameter("patron", value);
		retorno = (List<SisDetallefacturaservicio>) query.getResultList();
		return retorno;
	}
	
	
	
}
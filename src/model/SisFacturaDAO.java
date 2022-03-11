package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SisFacturaDAO extends ClaseDAO {

	//Metodos para que retorne todo los tipos de clientes 
	public List<SisFactura> getFacturaRellena(){
		List<SisFactura> retorno = new ArrayList<SisFactura>();
		
		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("SisFactura.findAll");
		retorno = (List<SisFactura>)query.getResultList();
		return retorno;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SisFactura> getListaIdFactura(int value) {
		List<SisFactura> retorno = new ArrayList<SisFactura>();
		
		
		Query query = getEntityManager().createNamedQuery("SisFacturaBusca.findAll").setParameter("patron", value);
		retorno = (List<SisFactura>) query.getResultList();
		return retorno;
	}
	
	
	public List<SisFactura> getFacturaparaEliminar(int value){
		List<SisFactura> retorno = new ArrayList<SisFactura>();
		
		//Hacer el Query
		Query query = getEntityManager().createNamedQuery("SisFacturaBuscaActivas.findAll").setParameter("patron", value);
		retorno = (List<SisFactura>)query.getResultList();
		return retorno;
	}
	
}
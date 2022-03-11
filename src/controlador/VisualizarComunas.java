package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.gmaps.Ginfo;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.LatLng;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import model.SegPermiso;
import model.SegUsuario;
import model.SegUsuarioDAO;





@SuppressWarnings({ "serial", "rawtypes" })
public class VisualizarComunas extends SelectorComposer{


	SegUsuarioDAO usuariodao = new SegUsuarioDAO();
	List<SegUsuario> listacomuneros =  new ArrayList<SegUsuario>();
	List<Gmarker> historyListMarker = new ArrayList<Gmarker>(),
			newListMarker = new ArrayList<Gmarker>();

	Gmarker marker = null;
	@Wire Gmaps mymap;
	Ginfo ginfo;


	


	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException{
		Selectors.wireComponents(view, this, false);	

		recuperar_marcadores();
		//onMapReady(mymap);
	}





	public void recuperar_marcadores() {


		listacomuneros = usuariodao.getListaUbicaciones();

		System.out.print("este es el tamaño: " + listacomuneros.size());

		for(SegUsuario det: listacomuneros) {


			final LatLng pointCoord = new LatLng( det.getLatitud(), det.getLongitud()); 
			final String strId = det.getCreatedAtDate().toString()  + ", " + det.getCreatedAtTime().toString();  
            final String titulo = det.getApellidoPaterno() + " " + det.getApellidoMaterno() + " "
            		            + det.getNombre();
            final String Direcciones = det.getCelular();
			
            marker = new  Gmarker(strId, pointCoord);
            
			marker.setId( strId );
	        
			marker.setTooltiptext(titulo);
			
			
			marker.setContent("<p>Nombre: </p>"
					+ titulo + "<p>Telefono: </p>" + Direcciones);
			
			
	
			marker.setOpen( false );
			
			//agregarle informacion

			historyListMarker.add(marker);

		}


		
		for(Gmarker marker : historyListMarker){

			mymap.setLat(marker.getLat());
			mymap.setLng(marker.getLng());
            mymap.setTooltiptext(marker.getTooltiptext());    
		         
            
			marker.setOpen(false);
			marker.setParent(mymap);
	
		}	
		

	}

    
	

	
	
	@Command
	public void getCoordenadas(@ContextParam(ContextType.TRIGGER_EVENT) MapMouseEvent event){
		
		 Gmarker gmarker = event.getGmarker();
	        if(gmarker != null) {
	            gmarker.setOpen(true);
	        }
		
	}
	
}







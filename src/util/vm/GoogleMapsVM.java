package util.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zul.Messagebox;

public class GoogleMapsVM {

	public static Double Glob_latitud = (double) 0;
	public static Double Glob_longitud =(double) 0;
	public static int Glob_verificaSelecion = 0;
 @Command
 public void getCoordenadas(@ContextParam(ContextType.TRIGGER_EVENT) MapMouseEvent event){
  Messagebox.show("Has hecho clic en las coordenadas: Lat: "+event.getLat()+" Lng: "+event.getLng()); 

  Glob_latitud = event.getLat();
  Glob_longitud = event.getLng();
  
  Glob_verificaSelecion = 1;
 }

}




package co.com.ceiba.dominio.controlador;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.dominio.Carro;
import co.com.ceiba.dominio.Moto;
import co.com.ceiba.dominio.excepcion.IngresoVehiculoExcepcion;
import co.com.ceiba.dominio.respuesta.FormatoRespuesta;
import co.com.ceiba.dominio.servicio.ServicioVigilante;

@RestController
public class VigilanteController {
	
	private final String EL_VEHICULO_HA_SIDO_REGISTRADO = "El vehiculo ha sido registrado correctamente";
	
	@Autowired
	private ServicioVigilante servicioVigilante;

    @RequestMapping(path = "/agregar/carro", method = RequestMethod.POST)
    public Object registrarIngresoCarro(@RequestBody Carro carro) {
    	
    	String placa = carro.getPlaca();
    	Date fechaIngreso = carro.getFechaIngreso();
    	
    	try {
    	
    	if(servicioVigilante.hayCupoCarro() && servicioVigilante.puedeIngresar(placa, fechaIngreso)) {
    		servicioVigilante.ingresarVehiculo(placa, fechaIngreso);
    		
    	}
    	return new FormatoRespuesta(EL_VEHICULO_HA_SIDO_REGISTRADO, true, carro, new Date());
    	
    	} catch (IngresoVehiculoExcepcion e) {
    		return new FormatoRespuesta(e.getMessage(), false, new Date());
    	}
    	
    }
    
    @RequestMapping(path = "/agregar/moto", method = RequestMethod.POST)
    public Object registrarIngresoMoto(@RequestBody Moto moto) {
    	
    	String placa = moto.getPlaca();
    	short cilindrada = moto.getCilindrada();
    	Date fechaIngreso = moto.getFechaIngreso();
    	
    	try {
    	
	    	if(servicioVigilante.hayCupoMoto() && servicioVigilante.puedeIngresar(placa, fechaIngreso)) {
	    		servicioVigilante.ingresarVehiculo(placa, cilindrada, fechaIngreso);
	    		
	    	}
	    	return new FormatoRespuesta(EL_VEHICULO_HA_SIDO_REGISTRADO, true, moto, new Date());
    	} catch (IngresoVehiculoExcepcion e) {
    		return new FormatoRespuesta(e.getMessage(), false, new Date());
    	}
        
    }
}


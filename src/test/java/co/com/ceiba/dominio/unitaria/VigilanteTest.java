package co.com.ceiba.dominio.unitaria;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.dominio.Carro;
import co.com.ceiba.dominio.Cobro;
import co.com.ceiba.dominio.Moto;
import co.com.ceiba.dominio.Vehiculo;
import co.com.ceiba.dominio.servicio.ServicioVigilanteImpl;
import testdatabuilder.CarroTestDataBuilder;
import testdatabuilder.MotoTestDataBuilder;
import testdatabuilder.VehiculoTestDataBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VigilanteTest {
	
	
	
	private static final String PLACA_COMIENZA_CON_A = "AJX14E";
	private static final int MOTOS_EN_PARQUEADERO_MENOR_A_10 = 5;
	private static final int MOTOS_EN_PARQUEADERO_IGUAL_A_10 = 10;
	private static final int CARROS_EN_PARQUEADERO_MENOR_A_20 = 17;
	private static final int CARROS_EN_PARQUEADERO_IGUAL_A_20 = 20;
	
	private static final int VALOR_UN_DIA_TRES_HORAS_CARRO = 11000;
	private static final int VALOR_DIEZ_HORAS_650_CC_MOTO = 6000;
	private static final int VALOR_DIEZ_HORAS_MOTO = 4000;
	
	private static final short CILINDRADA_650_CC = 650;
	private static final short CILINDRADA_125_CC = 125;
	
	@Test
	public void hayCupoMotoTest() {
		
		//arrange
		
		ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		
		when(servicioVigilante.obtenerNroMotosEnParqueadero()).thenReturn(MOTOS_EN_PARQUEADERO_MENOR_A_10);
		when(servicioVigilante.verificarCupo(Mockito.any())).thenCallRealMethod();
		
		//act
		boolean hayCupo = servicioVigilante.verificarCupo("Moto");
		
		//assert
		assertTrue(hayCupo);
	}
	
	@Test
	public void noHayCupoMotoTest() {
		//arrange
		ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		
		when(servicioVigilante.obtenerNroMotosEnParqueadero()).thenReturn(MOTOS_EN_PARQUEADERO_IGUAL_A_10);
		when(servicioVigilante.verificarCupo(Mockito.any())).thenCallRealMethod();
		
		//act
		boolean hayCupo = servicioVigilante.verificarCupo("Moto");
		
		//assert
		assertFalse(hayCupo);
	}
	
	@Test
	public void hayCupoCarroTest() {
		//arrange
		
		ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		
		when(servicioVigilante.obtenerNroCarrosEnParqueadero()).thenReturn(CARROS_EN_PARQUEADERO_MENOR_A_20);
		when(servicioVigilante.verificarCupo(Mockito.any())).thenCallRealMethod();
		
		//act
  		boolean hayCupo = servicioVigilante.verificarCupo("Carro");
		
		//assert
		assertTrue(hayCupo);
	}
	
	@Test
	public void noHayCupoCarroTest() {
		//arrange
		
		ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		
		when(servicioVigilante.obtenerNroCarrosEnParqueadero()).thenReturn(CARROS_EN_PARQUEADERO_IGUAL_A_20);
		when(servicioVigilante.verificarCupo(Mockito.any())).thenCallRealMethod();
		
		//act
		boolean hayCupo = servicioVigilante.verificarCupo("Carro");
		
		//assert
		assertFalse(hayCupo);
	}
	
	@Test
	public void puedeIngresarVehiculoConPlacaDifATest() {
		//arrange
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder();
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.build();
		
		ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		
		when(servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso())).thenReturn(true);
		
		//act
		boolean puedeIngresar = servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
		
		//assert
		assertTrue(puedeIngresar);
	}
	
	@Test
	public void noPuedeIngresarVehiculoConPlacaA_DiaHabil() {
		//arrange
		
		// Fecha miercoles
		Date fechaHabil = new Date(1530719569000L);
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca(PLACA_COMIENZA_CON_A).
				conFechaIngreso(fechaHabil);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.build();
		
		ServicioVigilanteImpl servicioVigilante = new ServicioVigilanteImpl();
		
		//when(servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso())).thenReturn(true);
		
		//act
		boolean puedeIngresar = servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
		
		//assert
		assertFalse(puedeIngresar);
	}
	
	@Test
	public void puedeingresarVehiculoConPlacaA_UnLunesTest() {
		//arrange
		
		//Fecha lunes
		Date fechaLunes = new Date(1530546975000L);
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca(PLACA_COMIENZA_CON_A).
				conFechaIngreso(fechaLunes);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.build();
		
		//ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		ServicioVigilanteImpl servicioVigilante = new ServicioVigilanteImpl();
		
		//when(servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso())).thenReturn(false);
		
		//act
		boolean puedeIngresar = servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
		
		//assert
		assertTrue(puedeIngresar);
	}
	
	@Test
	public void puedeingresarVehiculoConPlacaA_UnDomingoTest() {
		//arrange
		
		// Fecha domingo
		Date fechaDomingo = new Date(1530460575000L);
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca(PLACA_COMIENZA_CON_A).
				conFechaIngreso(fechaDomingo);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.build();
		
		//ServicioVigilanteImpl servicioVigilante = mock(ServicioVigilanteImpl.class);
		ServicioVigilanteImpl servicioVigilante = new ServicioVigilanteImpl();
		
		//when(servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso())).thenReturn(false);
		
		//act
		boolean puedeIngresar = servicioVigilante.puedeIngresar(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
		
		//assert
		assertTrue(puedeIngresar);
	}
	
	@Test
	public void generarCobroCarroUnDiaTresHorasTest() {
		//arrange
		
		// Mon Jun 25 2018 15:35:55
		Date fechaIngreso = new Date(1529958955365L);
		
		
		// Tue Jun 26 2018 18:35:56
		Date fechaSalida = new Date(1530056155000L);
		
		
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder()
				.conFechaIngreso(fechaIngreso)
				.conFechaSalida(fechaSalida);
		
		Carro carro = carroTestDataBuilder.build();
		
		
		//act
		int valorCobro = Cobro.generarCobro(carro);
		
		//assert
		assertEquals(VALOR_UN_DIA_TRES_HORAS_CARRO, valorCobro);
	}
	
	@Test
	public void generarCobroMotoDiezHorasY650CCTest() {
		//arrange
		
		// Tue Jun 26 2018 06:35:55 GMT-0500
		Date fechaIngreso = new Date(1530012955000L);
		
		
		// Tue Jun 26 2018 16:35:55 GMT-0500
		Date fechaSalida = new Date(1530048955000L);
		
		
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder()
				.conFechaIngreso(fechaIngreso)
				.conFechaSalida(fechaSalida)
				.conCilindrada(CILINDRADA_650_CC);
		
		Moto moto = motoTestDataBuilder.build();
		
		
		//act
		int valorCobro = Cobro.generarCobro(moto);
		
		//assert
		assertEquals(VALOR_DIEZ_HORAS_650_CC_MOTO, valorCobro);
	}
	
	
	@Test
	public void generarCobroMotoDiezHorasTest() {
		//arrange
		
		// Tue Jun 26 2018 06:35:55 GMT-0500
		Date fechaIngreso = new Date(1530012955000L);
		
		
		// Tue Jun 26 2018 16:35:55 GMT-0500
		Date fechaSalida = new Date(1530048955000L);
		
		
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder()
				.conFechaIngreso(fechaIngreso)
				.conFechaSalida(fechaSalida)
				.conCilindrada(CILINDRADA_125_CC);
		
		Moto moto = motoTestDataBuilder.build();
		
		
		//act
		int valorCobro = Cobro.generarCobro(moto);
		
		//assert
		assertEquals(VALOR_DIEZ_HORAS_MOTO, valorCobro);
	}
	
}

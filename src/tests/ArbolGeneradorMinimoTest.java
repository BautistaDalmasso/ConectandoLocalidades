package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import negocio.GrafoCompletoLocalidades;
import negocio.GrafoLocalidades;
import negocio.Localidad;

public class ArbolGeneradorMinimoTest {
	@Test
	public void arbolGeneradorMinimoSinLocalidadesTest() {
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		assertEquals(0, grafoCompleto.getArbolGeneradorMinimo().getTamanio());
	}
	
	@Test
	public void arbolGeneradorMinimoUnaLocalidadTest() {
		Localidad localidadCentro = new Localidad("centro", "Buenos Aires", 0, 0);
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		grafoCompleto.agregarLocalidad(localidadCentro);
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		assertEquals(1, grafoCompleto.getArbolGeneradorMinimo().getTamanio());
	}
	
	@Test
	public void arbolGeneradorMinimoTresLocalidadesTest() {
		GrafoCompletoLocalidades grafoCompleto = crearGrafoTresLocalidades();
		GrafoLocalidades arbolEsperado = crearArbolEsperadoTresLocalidades();
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		assertEquals(arbolEsperado, grafoCompleto.getArbolGeneradorMinimo());
	}
	
	private GrafoCompletoLocalidades crearGrafoTresLocalidades() {
		Localidad localidadCentro = new Localidad("centro", "Buenos Aires", 0, 0);
		Localidad localidadEste = new Localidad("Este", "Buenos Aires", -1, 0);
		Localidad localidadOeste = new Localidad("Oeste", "Buenos Aires", 1, 0);
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		grafoCompleto.agregarLocalidad(localidadCentro);
		grafoCompleto.agregarLocalidad(localidadOeste);
		grafoCompleto.agregarLocalidad(localidadEste);
		return grafoCompleto;
	}
	
	private GrafoLocalidades crearArbolEsperadoTresLocalidades() {
		Localidad localidadCentro = new Localidad("centro", "Buenos Aires", 0, 0);
		Localidad localidadEste = new Localidad("Este", "Buenos Aires", -1, 0);
		Localidad localidadOeste = new Localidad("Oeste", "Buenos Aires", 1, 0);
		GrafoLocalidades arbolEsperado = new GrafoLocalidades();
		arbolEsperado.agregarLocalidad(localidadCentro);
		arbolEsperado.agregarLocalidad(localidadOeste);
		arbolEsperado.agregarLocalidad(localidadEste);
		arbolEsperado.agregarConexion(localidadCentro, localidadOeste);
		arbolEsperado.agregarConexion(localidadCentro, localidadEste);
		return arbolEsperado;
	}
}

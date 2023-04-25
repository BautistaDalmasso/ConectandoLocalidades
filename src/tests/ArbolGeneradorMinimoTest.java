package tests;

import org.junit.Assert;
import org.junit.Test;

import negocio.GrafoCompletoLocalidades;
import negocio.GrafoLocalidades;
import negocio.Localidad;

public class ArbolGeneradorMinimoTest {
	@Test
	public void arbolGeneradorMinimoSinLocalidadesTest() {
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		Assert.assertEquals(0, grafoCompleto.getArbolGeneradorMinimo().getTamanio());
	}
	
	@Test
	public void arbolGeneradorMinimoUnaLocalidadTest() {
		Localidad localidadCentro = new Localidad("centro", "Buenos Aires", 0, 0);
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		grafoCompleto.agregarLocalidad(localidadCentro);
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		Assert.assertEquals(1, grafoCompleto.getArbolGeneradorMinimo().getTamanio());
	}
	
	@Test
	public void arbolGeneradorMinimoTresLocalidadesTest() {
		Localidad localidadCentro = new Localidad("centro", "Buenos Aires", 0, 0);
		Localidad localidadOeste = new Localidad("Este", "Buenos Aires", -1, 0);
		Localidad localidadEste = new Localidad("Oeste", "Buenos Aires", 1, 0);
		GrafoCompletoLocalidades grafoCompleto = new GrafoCompletoLocalidades();
		grafoCompleto.agregarLocalidad(localidadCentro);
		grafoCompleto.agregarLocalidad(localidadOeste);
		grafoCompleto.agregarLocalidad(localidadEste);
		GrafoLocalidades arbolEsperado = new GrafoLocalidades();
		arbolEsperado.agregarLocalidad(localidadCentro);
		arbolEsperado.agregarLocalidad(localidadOeste);
		arbolEsperado.agregarLocalidad(localidadEste);
		arbolEsperado.agregarConexion(localidadCentro, localidadOeste);
		arbolEsperado.agregarConexion(localidadCentro, localidadEste);
		
		grafoCompleto.construirArbolGeneradorMinimo();
		
		Assert.assertEquals(arbolEsperado, grafoCompleto.getArbolGeneradorMinimo());
	}
}

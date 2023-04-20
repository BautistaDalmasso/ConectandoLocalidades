package tests;

import org.junit.Assert;
import org.junit.Test;

import negocio.GrafoCompletoLocalidades;
import negocio.GrafoLocalidades;
import negocio.Localidad;

public class ArbolGeneradorMinimoTest {
	@Test
	public void arbolGeneradorMinimoSeObtieneCorrectamenteTest() {
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

package tests;

import org.junit.Assert;
import org.junit.Test;

import negocio.PosicionGeografica;


public class PosicionGeograficaTest {
	private static final double ERROR_ACEPTABLE_PORCENTAJE = 0.005;
	
	@Test(expected = IllegalArgumentException.class)
	public void latitudNegativaInvalidaTest() {
		new PosicionGeografica(-91, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void latitudPositivaInvalidaTest() {
		new PosicionGeografica(91, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void longitudNegativaInvalidaTest() {
		new PosicionGeografica(0, -181);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void longitudPositivaInvalidaTest() {
		new PosicionGeografica(0, 181);
	}
	
	@Test
	public void latitudYLongitudNegativasValidasTest() {
		new PosicionGeografica(-90, -180);
	}
	
	@Test
	public void latitudYLongitudPositivasValidasTest() {
		new PosicionGeografica(90, 180);
	}
	
	@Test
	public void distanciaEntreDosPosicionesTest() {
		PosicionGeografica salida = new PosicionGeografica(21, 53);
		PosicionGeografica llegada = new PosicionGeografica(55, 66);
		
		Double distancia = 3938.05;
		
		assertResultadoDentroDelMargenDeError(distancia, PosicionGeografica.distanciaEnKilometros(salida, llegada));
	}
	
	public void assertResultadoDentroDelMargenDeError(Double resultadoEsperado, Double resultadoObtenido) {
		Double porcentajeDeError = ((resultadoObtenido - resultadoEsperado) / resultadoEsperado) * 100;
		
		Assert.assertTrue(porcentajeDeError <= ERROR_ACEPTABLE_PORCENTAJE);
	}
}

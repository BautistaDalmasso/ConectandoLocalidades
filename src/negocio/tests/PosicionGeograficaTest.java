package negocio.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import negocio.PosicionGeografica;


public class PosicionGeograficaTest {
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
}

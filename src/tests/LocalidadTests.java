package tests;

import org.junit.Test;

import negocio.Localidad;


public class LocalidadTests {
	@Test (expected = IllegalArgumentException.class)
	public void inicializarConNombreNullTest() {
		new Localidad(null, "Buenos Aires", 0, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void inicializarConNombreVacioTest() {
		new Localidad("", "Buenos Aires", 0, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void inicializarConProvinciaNullTest() {
		new Localidad("La Boca", null, 0, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void inicializarConProvinciaVaciaTest() {
		new Localidad("La Boca", "", 0, 0);
	}
	
	@Test
	public void inicializarConValoresValidosTest() {
		new Localidad("La Boca", "Buenos Aires", 0, 0);
	}
}

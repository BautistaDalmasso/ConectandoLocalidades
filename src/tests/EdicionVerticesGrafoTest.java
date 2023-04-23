package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import negocio.ConexionLocalidades;
import negocio.GrafoCompletoLocalidades;
import negocio.Localidad;


public class EdicionVerticesGrafoTest {
	
	GrafoCompletoLocalidades grafo;
	Localidad laPlata;
	
	@Before
	public void setTest() {
		grafo = new GrafoCompletoLocalidades();
		laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarLocalidadNulaTest() {
		grafo.agregarLocalidad(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLocalidadRepetidaTest() {
		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(laPlata);
	}

	@Test
	public void localidadNoAgregadaNoExisteTest() {
		Assert.assertFalse(grafo.localidadExiste(laPlata));
	}

	@Test
	public void localidadExisteLuegoDeAgregarlaTest() {
		Localidad laPlataRepetida = new Localidad("La Plata", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);

		Assert.assertTrue(grafo.localidadExiste(laPlataRepetida));
	}

	@Test
	public void grafoSeCompletaAlAgregarLocalidades() {
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);

		ConexionLocalidades nuevaConexion = new ConexionLocalidades(laPlata, almiranteBrown);

		Assert.assertTrue(grafo.obtenerConexiones(laPlata).contains(nuevaConexion));
	}
	
	@Test
	public void costoSeActualizaTest() {
		Localidad avellaneda = new Localidad("Avellaneda", "Buenos Aires", 1, 1);
		Localidad rosario = new Localidad("Rosario", "Santa Fe", 10, 10);
		
		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(avellaneda);
		Integer costoParcial = grafo.getCostoConexionMaxima();
		
		grafo.agregarLocalidad(rosario);
		Integer costoFinal = grafo.getCostoConexionMaxima();
		
		assertTrue(costoParcial != costoFinal);
	}
	
	
}

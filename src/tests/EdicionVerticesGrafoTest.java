package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import negocio.ConexionLocalidades;
import negocio.GrafoCompletoLocalidades;
import negocio.Localidad;

public class EdicionVerticesGrafoTest {
	@Test(expected = IllegalArgumentException.class)
	public void agregarLocalidadNulaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();

		grafo.agregarLocalidad(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLocalidadRepetidaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(laPlata);
	}

	@Test
	public void localidadNoAgregadaNoExisteTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);

		assertFalse(grafo.localidadExiste(laPlata));
	}

	@Test
	public void localidadExisteLuegoDeAgregarlaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad laPlataRepetida = new Localidad("La Plata", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);

		assertTrue(grafo.localidadExiste(laPlataRepetida));
	}

	@Test
	public void grafoSeCompletaAlAgregarLocalidades() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);

		ConexionLocalidades nuevaConexion = new ConexionLocalidades(laPlata, almiranteBrown);

		assertTrue(grafo.obtenerConexiones(laPlata).contains(nuevaConexion));
	}
}

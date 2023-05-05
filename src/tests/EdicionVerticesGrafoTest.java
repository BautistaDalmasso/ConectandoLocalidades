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

	@Test(expected = IllegalArgumentException.class)
	public void eliminarLocalidadNulaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		
		grafo.eliminarLocalidad(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarLocalidadNoAgregadaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);

		grafo.eliminarLocalidad(laPlata);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarLocalidadDosVecesTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		grafo.agregarLocalidad(laPlata);
		
		grafo.eliminarLocalidad(laPlata);
		grafo.eliminarLocalidad(laPlata);
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
	public void localidadMismoNombreProvinciaDistintaTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata1 = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad laPlata2 = new Localidad("La Plata", "La Pampa", 1, 1);
		
		grafo.agregarLocalidad(laPlata1);
		grafo.agregarLocalidad(laPlata2);
		
		assertTrue(grafo.localidadExiste(laPlata2));
	}
	
	@Test
	public void grafoSeCompletaAlAgregarLocalidadesTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);

		ConexionLocalidades nuevaConexion = new ConexionLocalidades(laPlata, almiranteBrown);

		assertTrue(grafo.obtenerConexiones(laPlata).contains(nuevaConexion));
	}
	
	@Test
	public void localidadesSeAgreganConIndiceCorrectoTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);
		Localidad belgrano = new Localidad("Belgrano", "Buenos Aires", 0, 0);
		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);
		grafo.agregarLocalidad(belgrano);
		
		assertEquals(2, (int) grafo.getIndiceLocalidad(belgrano));
	}
	
	@Test
	public void localidadEliminadaNoExisteTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		grafo.agregarLocalidad(laPlata);
		
		grafo.eliminarLocalidad(laPlata);
		
		assertFalse(grafo.localidadExiste(laPlata));
	}
	
	@Test
	public void indicesSeActualizanLuegoDeEliminarLocalidadTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);
		Localidad belgrano = new Localidad("Belgrano", "Buenos Aires", 0, 0);
		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);
		grafo.agregarLocalidad(belgrano);
		
		grafo.eliminarLocalidad(almiranteBrown);
		
		assertEquals(1, (int) grafo.getIndiceLocalidad(belgrano));
	}
}

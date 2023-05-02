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
	public void agregarLocalidadNombreUnicoRepetidoTest() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata1 = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad laPlata2 = new Localidad("La Plata", "Buenos Aires", 1, 1);
		
		grafo.agregarLocalidad(laPlata1);
		grafo.agregarLocalidad(laPlata2);
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
	public void grafoSeCompletaAlAgregarLocalidades() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);

		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);

		ConexionLocalidades nuevaConexion = new ConexionLocalidades(laPlata, almiranteBrown);

		assertTrue(grafo.obtenerConexiones(laPlata).contains(nuevaConexion));
	}
	
	@Test
	public void localidadesSeAgreganConIndiceCorrecto() {
		GrafoCompletoLocalidades grafo = new GrafoCompletoLocalidades();
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", 0, 0);
		
		grafo.agregarLocalidad(laPlata);
		grafo.agregarLocalidad(almiranteBrown);
		
		assertEquals(1, (int) grafo.getIndiceLocalidad(almiranteBrown));
	}
}
